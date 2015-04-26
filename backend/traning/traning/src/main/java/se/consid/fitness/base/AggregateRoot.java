/**
 *
 */
package se.consid.fitness.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

/**
 */
public class AggregateRoot {

	private static final ConcurrentMap<Class<?>, CompletableFuture<Optional<Method>>> METHODS = new ConcurrentHashMap<>();

	private final List<AbstractEvent> uncommittedEvents = new ArrayList<>();

	protected String id;
	protected int version = 0;
	protected long timestamp = 0;

	public Stream<AbstractEvent> getUncommittedEvents() {
		return uncommittedEvents.stream();
	}

	public void markChangesAsCommitted() {
		uncommittedEvents.clear();
	}

	public void recover(final Stream<AbstractEvent> history) {
		history.forEach(event -> invokeHandlerMethod(event));
	}

	protected int nextVersion() {
		return version + 1;
	}

	protected long now() {
		return System.currentTimeMillis();
	}

	public String id() {
		return id;
	}

	public int version() {
		return version;
	}

	public long timestamp() {
		return timestamp;
	}

	protected void applyChange(final AbstractEvent event) {
		invokeHandlerMethod(event);
		uncommittedEvents.add(event);
	}

	private void invokeHandlerMethod(final AbstractEvent event) {
		try {
			final Optional<Method> method = getMethod(event);
			if (method.isPresent()) {
				method.get().invoke(this, event);
				return;
			}
		} catch (final IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException("Unable to call event handler method for " + event.getClass().getName(), e);
		}

		throw new RuntimeException("Unable to call event handler method for " + event.getClass().getName());
	}

	private Optional<Method> getMethod(final AbstractEvent event) {
		final CompletableFuture<Optional<Method>> newMethod = new CompletableFuture<>();
		final CompletableFuture<Optional<Method>> oldMethod = METHODS.putIfAbsent(event.getClass(), newMethod);
		final CompletableFuture<Optional<Method>> futureMethod = oldMethod != null ? oldMethod : newMethod;

		if (!futureMethod.isDone()) {
			futureMethod.complete(createMethod(event));
		}

		try {
			return futureMethod.get();
		} catch (InterruptedException | ExecutionException e) {
			return Optional.empty();
		}
	}

	private Optional<Method> createMethod(final AbstractEvent event) {
		try {
			final Method method = getClass().getDeclaredMethod("handleEvent", event.getClass());
			method.setAccessible(true);

			return Optional.of(method);
		} catch (final SecurityException | NoSuchMethodException e) {
			return Optional.empty();
		}
	}

	public boolean hasUncommittedEvents() {
		return !uncommittedEvents.isEmpty();
	}

}

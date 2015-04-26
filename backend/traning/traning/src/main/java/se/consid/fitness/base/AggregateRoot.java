/**
 *
 */
package se.consid.fitness.base;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 */
public class AggregateRoot {

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
		uncommittedEvents.add(event);
	}

	public boolean hasUncommittedEvents() {
		return !uncommittedEvents.isEmpty();
	}

}

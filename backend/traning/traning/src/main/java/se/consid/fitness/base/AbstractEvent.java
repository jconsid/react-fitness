/**
 *
 */
package se.consid.fitness.base;

import java.util.UUID;

/**
 */
public abstract class AbstractEvent {

	private final UUID aggregateId;
	private final int version;
	private final long timestamp;

	protected AbstractEvent(final UUID aggregateId, final int version, final long timestamp) {
		this.aggregateId = aggregateId;
		this.version = version;
		this.timestamp = timestamp;
	}

	public UUID getAggregateId() {
		return aggregateId;
	}

	public int getVersion() {
		return version;
	}

	public long getTimestamp() {
		return timestamp;
	}

}

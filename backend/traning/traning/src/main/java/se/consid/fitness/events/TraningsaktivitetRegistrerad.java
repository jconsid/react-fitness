/**
 *
 */
package se.consid.fitness.events;

import java.util.UUID;

import se.consid.fitness.base.AbstractEvent;

/**
 */
public class TraningsaktivitetRegistrerad extends AbstractEvent {

	public TraningsaktivitetRegistrerad(final UUID aggregateId, final int version, final long timestamp) {
		super(aggregateId, version, timestamp);
	}

}

/**
 *
 */
package se.consid.fitness.anvandare.domain;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import org.vertx.java.core.json.JsonObject;

/**
 */
public class Traningsaktivitet {

	private final UUID id;
	private final String namn;
	private final double traningsekvivalent;
	private final double minVarde;
	private final String enhet;
	private final Date[] period;

	private Traningsaktivitet(final JsonObject json) {
		id = UUID.fromString(json.getString("_id"));
		namn = json.getString("namn");
		traningsekvivalent = (Double) json.getNumber("traningsekvivalent");
		minVarde = (Double) json.getNumber("minVarde");
		enhet = json.getString("enhet");
		period = null;
	}

	public static Traningsaktivitet from(final JsonObject json) {
		return new Traningsaktivitet(json);
	}

	public UUID getId() {
		return id;
	}

	public String getNamn() {
		return namn;
	}

	public double getTraningsekvivalent() {
		return traningsekvivalent;
	}

	public double getMinVarde() {
		return minVarde;
	}

	public String getEnhet() {
		return enhet;
	}

	public Date[] getPeriod() {
		return period;
	}

	@Override
	public String toString() {
		return "Traningsaktivitet [id=" + id + ", namn=" + namn + ", traningsekvivalent=" + traningsekvivalent
				+ ", minVarde=" + minVarde + ", enhet=" + enhet + ", period=" + Arrays.toString(period) + "]";
	}

}

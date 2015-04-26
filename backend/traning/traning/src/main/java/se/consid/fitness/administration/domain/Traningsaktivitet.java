/**
 *
 */
package se.consid.fitness.administration.domain;

import java.util.Arrays;
import java.util.Date;

import org.vertx.java.core.json.JsonObject;

/**
 */
public class Traningsaktivitet {

	private final int id;
	private final String namn;
	private final double traningsekvivalent;
	private final int minVarde;
	private final String enhet;
	private final Date[] period;

	private Traningsaktivitet(final JsonObject json) {
		id = json.getInteger("_id");
		namn = json.getString("namn");
		traningsekvivalent = (Double) json.getNumber("traningsekvivalent");
		minVarde = (Integer) json.getNumber("minVarde");
		enhet = json.getString("enhet");
		period = null;
	}

	public static Traningsaktivitet from(final JsonObject json) {
		return new Traningsaktivitet(json);
	}

	public int getId() {
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

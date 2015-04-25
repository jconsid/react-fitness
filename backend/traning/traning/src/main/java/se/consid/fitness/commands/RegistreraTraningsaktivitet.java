/**
 *
 */
package se.consid.fitness.commands;

import java.util.Date;

import org.vertx.java.core.json.JsonObject;

import se.consid.fitness.base.AbstractCommand;

/**
 */
public class RegistreraTraningsaktivitet extends AbstractCommand {

	private final String anvandareId;
	private final String traningsaktivitetId;
	private final Date datum;
	private final double varde;

	private RegistreraTraningsaktivitet(final JsonObject json) {
		anvandareId = json.getString("anvandareId");
		traningsaktivitetId = json.getString("traningsaktivitetId");
		datum = new Date();
		varde = (Double) json.getNumber("varde");
	}

	public static RegistreraTraningsaktivitet from(final JsonObject json) {
		return new RegistreraTraningsaktivitet(json);
	}

	public String getAnvandareId() {
		return anvandareId;
	}

	public String getTraningsaktivitetId() {
		return traningsaktivitetId;
	}

	public Date getDatum() {
		return datum;
	}

	public double getVarde() {
		return varde;
	}

}

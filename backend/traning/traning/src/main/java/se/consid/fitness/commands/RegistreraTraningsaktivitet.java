/**
 *
 */
package se.consid.fitness.commands;

import java.util.Date;

import se.consid.fitness.base.AbstractCommand;

/**
 */
public class RegistreraTraningsaktivitet extends AbstractCommand {

	private final String anvandareId;
	private final String traningsaktivitetId;
	private final Date datum;
	private final double varde;

	public RegistreraTraningsaktivitet(final String anvandareId, final String traningsaktivitetId, final Date datum,
			final double varde) {
		this.anvandareId = anvandareId;
		this.traningsaktivitetId = traningsaktivitetId;
		this.datum = datum;
		this.varde = varde;
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

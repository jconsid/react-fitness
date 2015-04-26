/**
 *
 */
package se.consid.fitness.anvandare.domain;

import java.util.Date;

import org.vertx.java.core.json.JsonObject;

import se.consid.fitness.administration.domain.Traningsaktivitet;
import se.consid.fitness.commands.RegistreraTraningsaktivitet;

/**
 */
public class Traningstillfalle {

	private final double varde;
	private final Date datum;
	private final String namn;
	private final double poang;
	private final String enhet;

	private Traningstillfalle(final JsonObject json) {
		varde = (Double) json.getNumber("varde");
		datum = new Date();
		namn = json.getString("namn");
		poang = (Double) json.getNumber("poang");
		enhet = json.getString("enhat");
	}

	public Traningstillfalle(final Traningsaktivitet traningsaktivitet,
			final RegistreraTraningsaktivitet registreraTraningsaktivitet) {
		varde = registreraTraningsaktivitet.getVarde();
		datum = registreraTraningsaktivitet.getDatum();
		namn = traningsaktivitet.getNamn();
		poang = registreraTraningsaktivitet.getVarde() * traningsaktivitet.getTraningsekvivalent();
		enhet = traningsaktivitet.getEnhet();
	}

	public static Traningstillfalle from(final JsonObject json) {
		return new Traningstillfalle(json);
	}

	public static Traningstillfalle from(final Traningsaktivitet traningsaktivitet,
			final RegistreraTraningsaktivitet registreraTraningsaktivitet) {
		return new Traningstillfalle(traningsaktivitet, registreraTraningsaktivitet);
	}

	public Object toJson() {
		final JsonObject result = new JsonObject();
		result.putNumber("varde", varde);
		result.putString("datum", datum.toString());
		result.putString("namn", namn);
		result.putNumber("poang", poang);
		result.putString("enhet", enhet);

		return result;
	}

	public double getVarde() {
		return varde;
	}

	public Date getDatum() {
		return datum;
	}

	public String getNamn() {
		return namn;
	}

	public double getPoang() {
		return poang;
	}

	public String getEnhet() {
		return enhet;
	}

	@Override
	public String toString() {
		return "Traningstillfalle [varde=" + varde + ", datum=" + datum + ", namn=" + namn + ", poang=" + poang
				+ ", enhet=" + enhet + "]";
	}

}

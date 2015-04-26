/**
 *
 */
package se.consid.fitness.events;

import static java.lang.System.currentTimeMillis;
import static java.util.UUID.randomUUID;

import java.util.Date;

import org.vertx.java.core.json.JsonObject;

import se.consid.fitness.anvandare.domain.Anvandare;
import se.consid.fitness.anvandare.domain.Traningstillfalle;
import se.consid.fitness.base.AbstractEvent;

/**
 */
public class TraningsaktivitetRegistrerad extends AbstractEvent {

	private final double varde;
	private final Date datum;
	private final String namn;
	private final double poang;
	private final String enhet;
	private final Anvandare inloggadAnvandare;

	private TraningsaktivitetRegistrerad(final Anvandare anvandare, final Traningstillfalle tillfalle) {
		super(randomUUID(), 1, currentTimeMillis());
		varde = tillfalle.getVarde();
		datum = tillfalle.getDatum();
		namn = tillfalle.getNamn();
		poang = tillfalle.getPoang();
		enhet = tillfalle.getEnhet();
		inloggadAnvandare = anvandare;
	}

	public static TraningsaktivitetRegistrerad from(final Anvandare anvandare, final Traningstillfalle tillfalle) {
		return new TraningsaktivitetRegistrerad(anvandare, tillfalle);
	}

	@Override
	public JsonObject toJson() {
		final JsonObject result = new JsonObject();
		result.putString("id", getAggregateId().toString());
		result.putNumber("version", getVersion());
		result.putNumber("timestamp", getTimestamp());
		result.putNumber("varde", varde);
		result.putString("datum", datum.toString());
		result.putString("namn", namn);
		result.putNumber("poang", poang);
		result.putString("enhet", enhet);
		result.putObject("inloggadAnvandare", inloggadAnvandare.toJson());

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

	public Anvandare getInloggadAnvandare() {
		return inloggadAnvandare;
	}

}

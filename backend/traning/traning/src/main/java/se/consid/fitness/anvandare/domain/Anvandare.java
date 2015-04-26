/**
 *
 */
package se.consid.fitness.anvandare.domain;

import java.util.ArrayList;
import java.util.List;

import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

import se.consid.fitness.administration.domain.Traningsaktivitet;
import se.consid.fitness.base.AggregateRoot;
import se.consid.fitness.commands.RegistreraTraningsaktivitet;
import se.consid.fitness.events.TraningsaktivitetRegistrerad;

/**
 */
public class Anvandare extends AggregateRoot {

	private final String namn;
	private final String kontorstillhorighet;

	private final List<Traningstillfalle> traningstillfallen;

	private Anvandare(final JsonObject json) {
		id = json.getString("_id");
		namn = json.getString("namn");
		kontorstillhorighet = json.getString("kontorstillhorighet");
		traningstillfallen = new ArrayList<>();

		json.getArray("traningstillfallen")
		.forEach(j -> traningstillfallen.add(Traningstillfalle.from((JsonObject) j)));
	}

	public static Anvandare from(final JsonObject json) {
		return new Anvandare(json);
	}

	public JsonObject toJson() {
		final JsonObject result = new JsonObject();
		result.putString("_id", id);
		result.putString("namn", namn);
		result.putString("kontorstillhorighet", kontorstillhorighet);

		final JsonArray tillfallen = new JsonArray();
		traningstillfallen.forEach(t -> tillfallen.add(t.toJson()));
		result.putArray("traningstillfallen", tillfallen);

		return result;
	}

	public void registreraTraningsaktivitet(final Traningsaktivitet traningsaktivitet,
			final RegistreraTraningsaktivitet registreraTraningsaktivitet) {
		if (registreraTraningsaktivitet.getVarde() >= traningsaktivitet.getMinVarde()) {
			final Traningstillfalle tillfalle = Traningstillfalle.from(traningsaktivitet, registreraTraningsaktivitet);
			traningstillfallen.add(tillfalle);

			applyChange(TraningsaktivitetRegistrerad.from(this, tillfalle));
		}
	}

	@Override
	public String toString() {
		return "Anvandare [id=" + id + ", namn=" + namn + ", kontorstillhorighet=" + kontorstillhorighet + "]";
	}

}

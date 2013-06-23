package spi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import model.ResDef;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

@Api(name = "resDef", version = "v1", clientIds = { Ids.WEB_CLIENT_ID })
public class ResDefV1 {
	/*
	 * 
	 */
	@ApiMethod(name = "resdef.bye", path = "foos", httpMethod = HttpMethod.GET)
	public void getByeBye() {
		DatastoreServiceFactory.getDatastoreService().put(
				new Entity("John", 13));

	}

	@ApiMethod(name = "resdef.save", path = "save/{nom}", httpMethod = HttpMethod.PUT)
	public void save(@Named("nom") String name) {
		Entity entity = new Entity("ResDef");
		entity.setProperty("name", name);
		DatastoreServiceFactory.getDatastoreService().put(entity);
	}

	@ApiMethod(name = "resdef.remove", path = "remove/{id}", httpMethod = HttpMethod.DELETE)
	public void remove(@Named("id") String id) {
		DatastoreServiceFactory.getDatastoreService().delete(
				KeyFactory.createKey("ResDef", new Long(id)));
	}

	@ApiMethod(name = "resdef.add", path = "add", httpMethod = HttpMethod.PUT)
	public void add(ResDef resDef) {
		Entity entity = new Entity("ResDef");
		entity.setProperty("code", resDef.getCode());
		entity.setProperty("description", resDef.getDescription());
		DatastoreServiceFactory.getDatastoreService().put(entity);
	}

	@ApiMethod(name = "find", path = "find/{id}", httpMethod = HttpMethod.GET)
	public ResDef find(@Named("id") String id) {
		Entity entity;
		try {

			entity = DatastoreServiceFactory.getDatastoreService().get(
					KeyFactory.createKey("ResDef", new Long(id)));
			return new ResDef((String) entity.getProperty("code"),
					(String) entity.getProperty("description"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@ApiMethod(name = "listResDef", path = "listResDef", httpMethod = HttpMethod.GET)
	public List<ResDef> list() {
		System.out.println(ResDefV1.class.getName());
		DatastoreService datastoreService = DatastoreServiceFactory
				.getDatastoreService();
		List<ResDef> resDefs = new ArrayList<>();
		Query query = new Query("ResDef");
		Iterator<Entity> iterator = datastoreService.prepare(query)
				.asIterable(FetchOptions.Builder.withLimit(5)).iterator();
		while (iterator.hasNext()) {
			Entity entity = (Entity) iterator.next();
			resDefs.add(new ResDef((String) entity.getProperty("code"),
					(String) entity.getProperty("description")));
		}
		return resDefs;
	}

	@ApiMethod(name = "resDef.auth", path = "auth", httpMethod = HttpMethod.GET)
	public ResDef auth(final User user) throws OAuthRequestException,
			IOException {

		if (user != null) {
			return new ResDef("maxime", "mularz");
		} else {
			throw new OAuthRequestException("Invalid user.");
		}

	}
}

package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Transaction;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import models.*;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

/**
 * Manage a database of computers
 */
public class HomeController extends Controller {

	private FormFactory formFactory;

	@Inject
	public HomeController(FormFactory formFactory) {
		this.formFactory = formFactory;
	}

	/**
	 * This result directly redirect to application home.
	 */
	public Result GO_HOME = Results.redirect(routes.HomeController.list(0,
			"name", "asc", ""));

	/**
	 * Handle default path requests, redirect to computers list
	 */
	public Result index() {
		return GO_HOME;
	}

	/**
	 * Display the paginated list of computers.
	 *
	 * @param page
	 *            Current page number (starts from 0)
	 * @param sortBy
	 *            Column to be sorted
	 * @param order
	 *            Sort order (either asc or desc)
	 * @param filter
	 *            Filter applied on computer names
	 */
	public Result list(int page, String sortBy, String order, String filter) {
		return ok(views.html.list.render(
				Platotip.page(page, 10, sortBy, order, filter), sortBy, order,filter));
	}

	/**
	 * Display the 'edit form' of a existing Computer.
	 *
	 * @param id
	 *            Id of the computer to edit
	 */
	

	/**
	 * Handle the 'edit form' submission
	 *
	 * @param id
	 *            Id of the computer to edit
	 */
	public Result update(Long id) throws PersistenceException {
		Form<Platotip> platotipForm = formFactory.form(Platotip.class)
				.bindFromRequest();
		if (platotipForm.hasErrors()) {
			return badRequest(views.html.editForm.render(id, platotipForm));
		}

		Transaction txn = Ebean.beginTransaction();
		try {
			Platotip savedPlatotip = Platotip.find.byId(id);
			if (savedPlatotip != null) {
				Platotip newPlatotipData = platotipForm.get();
				
				savedPlatotip.discontinued = newPlatotipData.discontinued;
				savedPlatotip.introduced = newPlatotipData.introduced;
				savedPlatotip.name = newPlatotipData.name;

				savedPlatotip.update();
				flash("success", "Platotip " + platotipForm.get().name
						+ " has been updated");
				txn.commit();
			}
		} finally {
			txn.end();
		}

		return GO_HOME;
	}

	/**
	 * Display the 'new computer form'.
	 */
	public Result create() {
		Form<Platotip> platotipForm = formFactory.form(Platotip.class);
		return ok(views.html.createForm.render(platotipForm));
	}

	/**
	 * Handle the 'new computer form' submission
	 */
	public Result save() {

		Platotip c1 = new Platotip();
		Form<Platotip> platotipForm = formFactory.form(Platotip.class)
				.bindFromRequest();
		// Form<Computer> computerForm =
		// formFactory.form(Computer.class).fill(c1)
		// .bindFromRequest();

		if (platotipForm.hasErrors()) {
			return badRequest(views.html.createForm.render(platotipForm));
		}
		c1 = platotipForm.get();
		//c1.id = c1.id.MAX_VALUE + 1;
		c1.id = (long) 990;
		c1.save();
		// computerForm.get().save();
		flash("success", "Platotip " + platotipForm.get().name
				+ " has been created");
		return GO_HOME;
	}

	/**
	 * Handle computer deletion
	 */
	public Result delete(Long id) {
		Platotip.find.ref(id).delete();
		flash("success", "Platotip has been deleted");
		return GO_HOME;
	}

}

package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

/**
 * Computer entity managed by Ebean
 */
@Entity
public class Platotip extends Model {


	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	@Constraints.Required
	public String name;

	@Formats.DateTime(pattern = "yyyy-MM-dd")
	public Date introduced;

	@Formats.DateTime(pattern = "yyyy-MM-dd")
	public Date discontinued;

	@ManyToOne
	public Company company;

	/**
	 * Generic query helper for entity Computer with id Long
	 */
	public static Find<Long, Platotip> find = new Find<Long, Platotip>() {
	};

	/**
	 * Return a paged list of computer
	 *
	 * @param page
	 *            Page to display
	 * @param pageSize
	 *            Number of computers per page
	 * @param sortBy
	 *            Computer property used for sorting
	 * @param order
	 *            Sort order (either or asc or desc)
	 * @param filter
	 *            Filter applied on the name column
	 */
	public static PagedList<Platotip> page(int page, int pageSize,
			String sortBy, String order, String filter) {
		System.out.println("filter:" + filter);
		return find.where().ilike("name", "%" + filter + "%")
				.orderBy(sortBy + " " + order).fetch("company")
				.findPagedList(page, pageSize);

	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


}

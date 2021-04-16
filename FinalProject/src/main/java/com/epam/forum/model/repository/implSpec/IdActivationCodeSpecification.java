package com.epam.forum.model.repository.implSpec;

import java.util.ArrayList;
import java.util.List;

import com.epam.forum.model.entity.ActivationCode;
import com.epam.forum.model.repository.SearchCriterion;
import com.epam.forum.model.repository.Specification;

public class IdActivationCodeSpecification implements Specification<ActivationCode> {

	private static final String SQL_SELECT_ACTIVATION_CODE_BY_ID = "SELECT activationcodes.activationcode_id, activationcodes.creation_date, users.user_id, users.username, users.password, users.email, users.register_date, users.last_login_date, "
			+ "users.is_email_verifed, users.is_active, users.role FROM activationcodes JOIN users ON activationcodes.user_id = users.user_id WHERE ";

	private List<SearchCriterion> criterions;

	public IdActivationCodeSpecification(SearchCriterion searchCriteria) {
		criterions = new ArrayList<>();
		criterions.add(searchCriteria);
	}

	@Override
	public String toSqlQuery() {
		StringBuilder newQuery = new StringBuilder();
		newQuery.append(SQL_SELECT_ACTIVATION_CODE_BY_ID);
		for (SearchCriterion criteria : criterions) {
			String key = criteria.getKey();
			String operation = criteria.getOperation();
			newQuery.append(key);
			newQuery.append(operation);
			newQuery.append("?");
		}
		return newQuery.toString();
	}

	@Override
	public List<SearchCriterion> getSearchCriterions() {
		return criterions;
	}
}

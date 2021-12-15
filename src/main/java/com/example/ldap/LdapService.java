package com.example.ldap;

import java.util.List;
import java.util.Optional;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.query.SearchScope;
import org.springframework.stereotype.Service;
import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
public class LdapService {

	@Autowired
	private LdapTemplate ldapTemplate;
	
	
	public Optional<LdapPerson> search(String name){
		String result="";
		System.out.println("called");
		LdapQuery ldapQuery= LdapQueryBuilder.query()
				.searchScope(SearchScope.SUBTREE)
				.where("objectclass").is("person")
				.and("cn").like(name); 
		System.out.println("Query : "+ldapQuery);
		
		List<LdapPerson> list=ldapTemplate.search(ldapQuery,(Attributes attr)->
		LdapPerson.builder().firstName(getValue(attr,"cn"))
							.lastName(getValue(attr,"sn"))
							.build());
		
		
		return list.isEmpty() ? Optional.empty() :Optional.of(list.get(0));
	       
	   }


	private String getValue(Attributes attr, String key) throws javax.naming.NamingException {
		Attribute attribute = attr.get(key);
		
		String value="";
		try {
			value = attribute !=null ? attribute.get().toString() : null;	
			System.out.println("vaue "+ value);
		}
		catch(NamingException e) {
			throw new IllegalArgumentException("Exception looking up ldap attribute by key["+key+"]",e);
		}
		return value;
	}
	
}

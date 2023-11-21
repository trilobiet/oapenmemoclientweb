package org.oapen.memoproject.clientweb.jpa;

import java.util.List;
import java.util.Optional;

public interface SettingRepository extends ReadOnlyRepository<Setting, String> {
	
	List<Setting> findAll();
	
	Optional<Setting> findByKey(String key);

}

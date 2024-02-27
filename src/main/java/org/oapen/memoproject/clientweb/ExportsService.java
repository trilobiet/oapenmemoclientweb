package org.oapen.memoproject.clientweb;

import java.io.InputStream;

import org.springframework.data.util.Pair;

import io.vavr.control.Either;

public interface ExportsService {
	
	enum FailStatus {
		CLIENT_NOT_FOUND, FILE_NOT_FOUND, UNAUTHORIZED
	}

	Either<FailStatus, Pair<InputStream,String>> getExport(String homedir, String name, String accessKey);
	
}


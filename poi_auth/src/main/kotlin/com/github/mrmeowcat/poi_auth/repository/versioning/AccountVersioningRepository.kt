package com.github.mrmeowcat.poi_auth.repository.versioning

import com.github.mrmeowcat.poi_auth.document.AccountDocument
import org.springframework.stereotype.Repository

/**
 * Versioning repository for Account.
 */
@Repository
class AccountVersioningRepository : AbstractVersioningRepository<AccountDocument, String>()

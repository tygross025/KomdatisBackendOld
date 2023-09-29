package com.komdatis.repository

import com.komdatis.model.Certificate
import org.springframework.data.repository.CrudRepository

interface CertificateRepository : CrudRepository<Certificate, Int>
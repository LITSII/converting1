package com.makers.princemaker.repository

import com.makers.princemaker.code.StatusCode
import com.makers.princemaker.entity.Prince
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * @author Snow
 */
@Repository
interface PrinceRepository : JpaRepository<Prince, Long> {

    // 단건
    fun findByPrinceId(princeId: String): Prince?


    // 다건
    fun findByStatusEquals(status: StatusCode): List<Prince>
}

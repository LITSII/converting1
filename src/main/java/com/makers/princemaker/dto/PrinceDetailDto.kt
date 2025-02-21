package com.makers.princemaker.dto

import com.makers.princemaker.code.StatusCode
import com.makers.princemaker.entity.Prince
import com.makers.princemaker.entity.Prince.age
import com.makers.princemaker.entity.Prince.experienceYears
import com.makers.princemaker.entity.Prince.name
import com.makers.princemaker.entity.Prince.princeId
import com.makers.princemaker.entity.Prince.princeLevel
import com.makers.princemaker.entity.Prince.skillType
import com.makers.princemaker.entity.Prince.status
import com.makers.princemaker.type.PrinceLevel
import com.makers.princemaker.type.SkillType
import com.makers.princemaker.util.getLocalDateTimeString

/**
 * @author Snow
 */
class PrinceDetailDto {
    var princeLevel: PrinceLevel? = null
    var skillType: SkillType? = null
    var experienceYears: Int? = null
    var princeId: String? = null
    var name: String? = null
    var age: Int? = null
    var status: StatusCode? = null
    var birthDate: String? = null






    companion object {
        @JvmStatic
        fun fromEntity(prince: Prince): PrinceDetailDto {
            return PrinceDetailDto.builder()
                .princeLevel(prince.princeLevel)
                .skillType(prince.skillType)
                .experienceYears(prince.experienceYears)
                .princeId(prince.princeId)
                .name(prince.name)
                .age(prince.age)
                .status(prince.status)
                .birthDate(getLocalDateTimeString(prince.createdAt!!))
                .build()
        }
    }
}

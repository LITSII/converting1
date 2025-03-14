package com.makers.princemaker.service

import com.makers.princemaker.code.PrinceMakerErrorCode
import com.makers.princemaker.code.StatusCode
import com.makers.princemaker.constant.PrinceMakerConstant
import com.makers.princemaker.constant.PrinceMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS
import com.makers.princemaker.controller.CreatePrince

import com.makers.princemaker.entity.Prince
import com.makers.princemaker.entity.PrinceMock
import com.makers.princemaker.entity.dummyPrince
import com.makers.princemaker.exception.PrinceMakerException
import com.makers.princemaker.repository.PrinceRepository
import com.makers.princemaker.repository.WoundedPrinceRepository
import com.makers.princemaker.type.PrinceLevel
import com.makers.princemaker.type.PrinceLevel.JUNIOR_PRINCE
import com.makers.princemaker.type.PrinceLevel.MIDDLE_PRINCE
import com.makers.princemaker.type.SkillType
import com.makers.princemaker.type.SkillType.INTELLECTUAL
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

/**
 * @author Snow
 */
@ExtendWith(MockKExtension::class)
internal class PrinceMakerServiceTest {
    @RelaxedMockK
    lateinit var princeRepository: PrinceRepository

    @MockK
    lateinit var woundedPrinceRepository: WoundedPrinceRepository

    @InjectMockKs
    lateinit var princeMakerService: PrinceMakerService

    @Test
    fun princeTest() {
            //given
            val juniorPrince = dummyPrince(princeLevel = JUNIOR_PRINCE,
                skillType = INTELLECTUAL,
                experienceYears = MAX_JUNIOR_EXPERIENCE_YEARS)
//                PrinceMock.createPrince(
//                    PrinceLevel.JUNIOR_PRINCE, INTELLECTUAL,
//                    PrinceMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS, "princeId"
//                )
            every {
                princeRepository.findByPrinceId(any())
            } returns Optional.of(juniorPrince)

            //when
            val prince = princeMakerService.getPrince("princeId")

            //then
            assertEquals(PrinceLevel.JUNIOR_PRINCE, prince.princeLevel)
            assertEquals(INTELLECTUAL, prince.skillType)
            assertEquals(
                MAX_JUNIOR_EXPERIENCE_YEARS,
                prince.experienceYears
            )
        }

    @Test
    fun createPrinceTest_success() {
        //given
        val request = CreatePrince.Request(
            MIDDLE_PRINCE,
            INTELLECTUAL,
            7,
            "p;rinceId",
            "name",
            28
        )
        val slot = slot<Prince>()
        every { princeRepository.save(any() ) } returns Prince(
            id = null,
            princeLevel = PrinceLevel.BABY_PRINCE,
            skillType = SkillType.WARRIOR,
            status = StatusCode.HEALTHY,
            experienceYears = 1987,
            princeId = "porta",
            name = "Tom Morales",
            age = 5042,
            createdAt = null,
            updatedAt = null
        )

        //when
        val response = princeMakerService.createPrince(request)

        //then
        verify(exactly = 1) { princeRepository.save(capture(slot)) }
        val savedPrince = slot.captured
        assertEquals(MIDDLE_PRINCE, savedPrince.princeLevel)
        assertEquals(INTELLECTUAL, savedPrince.skillType)
        assertEquals(7, savedPrince.experienceYears)

        assertEquals(MIDDLE_PRINCE, response.princeLevel)
        assertEquals(INTELLECTUAL, response.skillType)
        assertEquals(7, response.experienceYears)
    }

    @Test
    fun createPrinceTest_failed_with_duplicated() {
        //given
        val juniorPrince =
            PrinceMock.createPrince(
                PrinceLevel.JUNIOR_PRINCE,
                INTELLECTUAL,
                PrinceMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS,
                "princeId"
            )
        val request = CreatePrince.Request(
            PrinceLevel.JUNIOR_PRINCE,
            INTELLECTUAL,
            3,
            "princeId",
            "name",
            28
        )
        every { princeRepository.findByPrinceId(any())  } returns Optional.of(juniorPrince)


        //when
        val exception =
            assertThrows(
                PrinceMakerException::class.java
            ) { princeMakerService.createPrince(request) }
        //then
        assertEquals(PrinceMakerErrorCode.DUPLICATED_PRINCE_ID, exception.princeMakerErrorCode)
    }

    @Test
    fun createPrinceTest_failed_with_invalid_experience() {
        //given
        val request = CreatePrince.Request(
            PrinceLevel.KING,
            INTELLECTUAL,
            PrinceMakerConstant.MIN_KING_EXPERIENCE_YEARS - 3,
            "princeId",
            "name",
            28
        )
       every { princeRepository.findByPrinceId(any())  } returns Optional.empty()

        //when
        val exception =
            assertThrows(
                PrinceMakerException::class.java
            ) { princeMakerService.createPrince(request) }
        //then
        assertEquals(
            PrinceMakerErrorCode.LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH,
            exception.princeMakerErrorCode
        )
    }
}

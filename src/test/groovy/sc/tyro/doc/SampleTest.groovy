package sc.tyro.doc

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static sc.tyro.core.Tyro.*
import static sc.tyro.doc.TyroExtension.BASE_URL

@ExtendWith(TyroExtension)
@DisplayName("Sample 1")
class SampleTest {
    @Test
    void sample_1() {
        visit BASE_URL + '/sample_1.html'

        // tag::sample_1[]
        field('Email').should {
            be visible
            be empty
        }

        field('Password').should {
            be visible
            be empty
        }

        dropdown('Language').should {
            be visible
            have 2.items
            have items('EN', 'FR')
            have selectedItem('EN')
        }
        // end::sample_1[]
    }

    @Test
    void sample_2() {
        visit BASE_URL + '/sample_2.html'

        // tag::sample_2[]
        radio("Male").should { be checked }
        radio("Female").should { be unchecked }

        check radio("Female")

        radio("Male").should { be unchecked }
        radio("Female").should { be checked }
        // end::sample_2[]
    }
}
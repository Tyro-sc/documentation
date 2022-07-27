package sc.tyro.doc

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import sc.tyro.bundle.html5.Div
import sc.tyro.bundle.html5.input.InputTypeRange
import sc.tyro.core.component.CheckBox
import sc.tyro.core.component.Dropdown
import sc.tyro.core.component.Panel
import sc.tyro.core.component.field.EmailField
import sc.tyro.core.component.field.Field
import sc.tyro.core.component.field.RangeField
import sc.tyro.core.input.Keyboard

import static sc.tyro.core.Tyro.*
import static sc.tyro.core.input.Key.SHIFT
import static sc.tyro.doc.TyroExtension.BASE_URL

@ExtendWith(TyroExtension)
@DisplayName("Interactions Tests")
class InteractionTest {
    @Test
    @DisplayName("Mouse")
    void mouse() {
        visit BASE_URL + '/interaction.html'

        Field component = field('Email', EmailField)
        Panel panel = $('#drag') as Div
        Panel dropZone = $('#drop-zone') as Div

        // tag::mouse[]
        component.click()

        component.doubleClick()

        component.rightClick()

        panel.drag().on(dropZone)
        // end::mouse[]

        // tag::mouse-dsl[]
        clickOn component

        doubleClickOn component

        rightClickOn component

        drag panel on dropZone
        // end::mouse-dsl[]

    }

    @Test
    @DisplayName("Keyboard")
    void keyboard() {
        visit BASE_URL + '/interaction.html'

        Field emailField = field('Email', EmailField)

        emailField.should { have value("") }
        clickOn emailField // To get the focus

        // tag::keyboard[]
        Keyboard keyboard = new Keyboard()

        keyboard.type([SHIFT, 'tyro'])
        // end::keyboard[]

        clear emailField
        emailField = field('Email', EmailField)

        emailField.should { have value("") }
        clickOn emailField // To get the focus

        // tag::keyboard-dsl-1[]
        type SHIFT + 'tyro'
        // end::keyboard-dsl-1[]
        emailField.should { have value('TYRO') }




    }

    @Test
    @DisplayName("Intention")
    void intention() {
        visit BASE_URL + '/interaction.html'

        // tag::check[]
        CheckBox checkBox = checkbox('Checkbox Label')
        checkBox.should { be unchecked }

        check checkBox

        checkBox.should { be checked }
        // end::check[]

        // tag::uncheck[]
        checkBox.should { be checked }

        uncheck checkBox

        checkBox.should { be unchecked }
        // end::uncheck[]

        // tag::fill[]
        Field emailField = field('Email')

        emailField.should { be empty }

        fill emailField with 'joe.black@email.org'

        emailField.should {
            be filled
            have value('joe.black@email.org')
        }
        // end::fill[]

        // tag::clear[]
        emailField.should {
            be filled
            have value('joe.black@email.org')
        }

        clear emailField

        emailField.should { be empty }
        // end::clear[]

        // tag::set[]
        RangeField range = field('Range', InputTypeRange)

        range.should { have value(10) }

        set range to 20

        range.should { have value(20) }
        // end::set[]

        // tag::on[]
        Dropdown language = dropdown('Language')
        language.should { have selectedItem('EN') }

        on language select 'FR'

        language.should { have selectedItem('FR') }
        // end::on[]
    }
}

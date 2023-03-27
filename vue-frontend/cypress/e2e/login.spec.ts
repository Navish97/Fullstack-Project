
export {}


describe('Login', () => {
    beforeEach(() => {
        cy.visit('http://localhost:3000/login')
    })

    it('Shows an error message for password mismatch', () => {
        cy.get('#inpEmail').type('admin@localhost')
        cy.get('#inpPassword').type('wrong password')
        cy.get('#button').click()

        // Assert that an error message is displayed for password mismatch
        cy.get('#error-message').should('contain.text', 'Email or password is incorrect.')
    })

    it('Login successful', () => {
        cy.get('#inpEmail').type('admin@localhost')
        cy.get('#inpPassword').type('admin')
        cy.get('#button').click()

        //assert that the user is redirected to the home page, and that the url does not contain anything after the base url
        cy.url().should('eq', 'http://localhost:3000/')
    })
})
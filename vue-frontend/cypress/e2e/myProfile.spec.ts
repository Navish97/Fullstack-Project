
export {}


describe('Login', () => {
    beforeEach(() => {
        cy.visit('http://localhost:3000/login')
        cy.get('#inpEmail').type('erik.skjellevik@lyse.net')
        cy.get('#inpPassword').type('1')
        cy.get('#button').click()

        //assert that the user is redirected to the home page, and that the url does not contain anything after the base url
        cy.url().should('eq', 'http://localhost:3000/')
    })

    it('Can access my profile page after login', () => {
        cy.visit('http://localhost:3000/my-profile')
        cy.url().should('eq', 'http://localhost:3000/my-profile')
    })

    it('Can log out', () => {
        cy.visit('http://localhost:3000/my-profile')
        cy.get('.log-out-btn').click()

        //assert that the user is redirected to the home page, and that the url does not contain anything after the base url
        cy.url().should('eq', 'http://localhost:3000/')
    })

    it('Can change name', () => {
        cy.visit('http://localhost:3000/my-profile')
        cy.get("#myAccountBtn").click(  )
        cy.url().should('eq', 'http://localhost:3000/my-profile/edit')

        cy.get('#inpName').clear().type('Erik')
        cy.get("#update").click()

        cy.url().should('eq', 'http://localhost:3000/my-profile')
        cy.get(".user-name").should('contain.text', 'Erik')
    })



})
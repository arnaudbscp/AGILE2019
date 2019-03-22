// @flow
import HomePage from './pages/HomePage.js';
import Plus from './pages/Plus.js';
import Inscription from './pages/InscriptionPage.js';
import Connexion from './pages/Connexion.js';
import PageRenderer from './PageRenderer.js';
import $ from 'jquery';
import Menu from './components/Menu.js';
import Admin from './pages/Admin.js';

// configuration du PageRenderer
PageRenderer.titleElement = document.querySelector('.pageTitle');
PageRenderer.contentElement = document.querySelector('.newsContainer');

// déclaration des différentes page de l'app
const homePage:HomePage = new HomePage([]);
const plus:Plus = new Plus();
const inscriptionPage:Inscription = new Inscription();
const connexion:Connexion = new Connexion();
const administrationPage:Admin= new Admin();

// configuration des liens du menu
const menu:Menu = new Menu();
const logoLink = $('a.navbar-brand');
const homeLink = $('.homeLink');
const PlusLink = $('.PlusLink');
const inscription = $('.inscriptionLink');
const connexionLink = $('.connexionLink');
const adminLink = $('.adminLink')

logoLink.click( (event:Event) => {
	event.preventDefault();
	renderHome();
});
homeLink.click( (event:Event) => {
	event.preventDefault();
	renderHome();
});
PlusLink.click( (event:Event) => {
	event.preventDefault();
	renderPlus();
})
inscription.click( (event:Event) => {
	event.preventDefault();
	renderInscription();
})
adminLink.click( (event:Event) => {
	event.preventDefault();
	renderAdministration();
})
connexionLink.click( (event:Event) => {
	event.preventDefault();
	renderConnexion();
})

function renderHome():void{
	menu.setSelectedLink(homeLink);
	PageRenderer.renderPage(homePage);
}
function renderPlus():void{
	menu.setSelectedLink(PlusLink);
	PageRenderer.renderPage(plus);
}
function renderInscription():void{
	menu.setSelectedLink(inscription);
	PageRenderer.renderPage(inscriptionPage);
}
function renderAdministration():void{
	menu.setSelectedLink(adminLink);
	PageRenderer.renderPage(administrationPage);
}
function renderConnexion():void{
	menu.setSelectedLink(connexionLink);
	PageRenderer.renderPage(connexion);
}
// lorsqu'on arrive sur l'appli, par défaut
// on affiche la page d'accueil
renderHome();
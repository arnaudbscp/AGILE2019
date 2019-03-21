// @flow
import HomePage from './pages/HomePage.js';
import AddPizzaPage from './pages/AddPizzaPage.js';
import Inscription from './pages/InscriptionPage.js';
import Connexion from './pages/Connexion.js';
import PageRenderer from './PageRenderer.js';
import $ from 'jquery';
import Menu from './components/Menu.js';

// configuration du PageRenderer
PageRenderer.titleElement = document.querySelector('.pageTitle');
PageRenderer.contentElement = document.querySelector('.newsContainer');

// déclaration des différentes page de l'app
const homePage:HomePage = new HomePage([]);
const addPizzaPage:AddPizzaPage = new AddPizzaPage();
const inscriptionPage:Inscription = new Inscription();
const connexion:Connexion = new Connexion();

// configuration des liens du menu
const menu:Menu = new Menu();
const logoLink = $('a.navbar-brand');
const homeLink = $('.homeLink');
const addPizzaLink = $('.addPizzaLink');
const inscription = $('.inscriptionLink');
const connexionLink = $('.connexionLink');

logoLink.click( (event:Event) => {
	event.preventDefault();
	renderHome();
});
homeLink.click( (event:Event) => {
	event.preventDefault();
	renderHome();
});
addPizzaLink.click( (event:Event) => {
	event.preventDefault();
	renderAddPizza();
})
inscription.click( (event:Event) => {
	event.preventDefault();
	renderInscription();
})

connexionLink.click( (event:Event) => {
	event.preventDefault();
	renderConnexion();
})

function renderHome():void{
	menu.setSelectedLink(homeLink);
	PageRenderer.renderPage(homePage);
}
function renderAddPizza():void{
	menu.setSelectedLink(addPizzaLink);
	PageRenderer.renderPage(addPizzaPage);
}
function renderInscription():void{
	menu.setSelectedLink(inscription);
	PageRenderer.renderPage(inscriptionPage);
}
function renderConnexion():void{
	menu.setSelectedLink(connexionLink);
	PageRenderer.renderPage(connexion);
}
// lorsqu'on arrive sur l'appli, par défaut
// on affiche la page d'accueil
renderHome();

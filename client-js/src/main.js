// @flow
import HomePage from './pages/HomePage.js';
import AddPizzaPage from './pages/AddPizzaPage.js';
import Inscription from './pages/InscriptionPage.js';
import PageRenderer from './PageRenderer.js';
import $ from 'jquery';
import Menu from './components/Menu.js';

// configuration du PageRenderer
PageRenderer.titleElement = document.querySelector('.pageTitle');
PageRenderer.contentElement = document.querySelector('.pizzasContainer');

// déclaration des différentes page de l'app
const homePage:HomePage = new HomePage([]);
const addPizzaPage:AddPizzaPage = new AddPizzaPage();
const inscriptionPage:Inscription = new Inscription();

// configuration des liens du menu
const menu:Menu = new Menu();
const logoLink = $('a.navbar-brand');
const homeLink = $('.homeLink');
const addPizzaLink = $('.addPizzaLink');
const inscription = $('.inscriptionLink');

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
// lorsqu'on arrive sur l'appli, par défaut
// on affiche la page d'accueil
renderHome();

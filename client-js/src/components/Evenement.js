// @flow
import Component from './Component.js';
import $ from 'jquery';

export default class Evenement extends Component {
	
	static getImg(categorie:String):String{
		switch(categorie){
			case "Location d`outils": return "../assets/outils.png";
			case "Cours": return "../assets/cours.png";
			case "Atelier thematique": return "../assets/atelier.png";
			case "Stage": return "../assets/stage.png";
			case "Tapisserie": return "../assets/tapisserie.png";
			default: return "../assets/tapisserie.png";
		}
	}

	constructor(evenement:{nom:string, categorie:string, heure:string, heureFin:string, id:number, date:string, place:string, reservations:Array, prix:string, description:string}){
		super('td', null, [
			new Component('form', {name:'class', value:'Evenement'}, [
				new Component('section', {name:'class', value: `${evenement.nom}`}, [
					new Component('h2', null, evenement.nom),
					new Component('img', {name:`style="width:80px;height:80px" src`, value:Evenement.getImg(evenement.categorie), alt:`bonjour`}, ``),
					new Component( 'ul', null, [
						new Component('p', null, `Catégorie : ${evenement.categorie}`),
						new Component('p', null, `Date : ${evenement.date}`),
						new Component('p', null, `Créneau : ${evenement.heure} à ${evenement.heureFin}`),
						new Component('p', null, `Places : ${evenement.reservations.length}/${evenement.place}`),
						new Component('p', null, `Description : <br/>${evenement.description}<br/>Prix à ${evenement.prix}€`),
						new Component('p', null, `Personnes déjà inscrites : ${evenement.reservations.reduce((a,b)=>a + " " + b.login, " ")}`),
                  new Component('input', {name:'type', value: "submit"})
					])
				])	
			])
		]);
		}

}

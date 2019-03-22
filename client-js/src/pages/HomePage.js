// @flow
import Component from '../components/Component.js';
import Evenement from '../components/Evenement.js';
import Page from './Page.js';
import $ from 'jquery';

export default class HomePage extends Page {
	#data;

	constructor( data:Array<{nom:string, heure:string, heureFin:string, id:number, date:string, place:string, reservations:Array, prix:string, description:string}> ){
		super( 'Les evenements à venir encore disponibles' );
		this.attribute = {name:'class', value:'newsContainer'};
		this.data = data;
	}

	set data(value:Array<{nom:string, heure:string, heureFin:string, id:number, date:string, place:string, reservations:Array, prix:string, description:string}>):void {
		this.#data = value;
		this.children = this.#data.map(evenements => new Evenement(evenements));
	}

	mount(events:HTMLElement):void {
		console.log("cc");
		fetch( 'http://localhost:8080/api/v1/events', {
			method:'GET',
			headers: { 'Content-Type': 'application/json' },
		})
		.then( (response:Response) => response.json() )
		.then( (data:any) => {
				document.querySelector('.newsContainer').innerHTML="";
				this.data = data;
				events.innerHTML += this.renderTab();
				// $FlowFixMe
				this.submit = this.submit.bind(this);
				$('form.Evenement').submit( this.submit );
				$('input').attr('value','Réserver');
		});
	}

	// Lien personnalisé selon l'evenement : on recupère le date/nom pour réserver
	submit(event:Event):void {
		let nbElements = $( 'form.Evenement' ).length;
		let terminaison = new Array();
		for(let i = 1; i <= nbElements; i++) {
			terminaison[i-1] = $(event.currentTarget).parent().children(`:nth-child(${i})`).children(':first-child').attr('class');
		}
		let c = 0;
		c = $(event.currentTarget).index();
		event.preventDefault();
		let cc = "";
		var name = "username" + "=";
  		var decodedCookie = decodeURIComponent(document.cookie);
  		var ca = decodedCookie.split(';');
  		for(var i = 0; i <ca.length; i++) {
    		var ccc = ca[i];
    		while (ccc.charAt(0) == ' ') {
      			ccc = ccc.substring(1);
    		}
    		if (ccc.indexOf(name) == 0) {
      			cc = ccc.substring(name.length, ccc.length);
    		}
  		}
		console.log(document.cookie);
		if(cc != "") {
			if(cc == 'admin') {
				alert('Vous êtes admin, impossible !');
				// traitement post direction serveur creation reservation
			}else {
				// à modifier
				fetch( `http://localhost:8080/api/v1/events/${terminaison[c]}/${cc}`, {
						method:'PUT',
						headers: { 'Content-Type': 'application/json' },
					})
				.then(response => {
					if (!response.ok) {
						throw new Error( `${response.status} : ${response.statusText}` );
					}
					alert(`Reservation faite, merci !`);
					//return response.json();
				})
				.catch( error => alert(`Enregistrement impossible`) );
			}
		}else {
			alert('Veuillez vous connecter !');
		}
	}
}
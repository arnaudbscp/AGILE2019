// @flow
import Component from '../components/Component.js';
import Evenement from '../components/Evenement.js';
import Page from './Page.js';
import $ from 'jquery';

export default class HomePage extends Page {
	#data;

	constructor( data:Array<{nom:string, heure:string, id:number, date:string}> ){
		super( 'Les evenements à venir' );
		this.attribute = {name:'class', value:'newsContainer'};
		this.data = data;
	}

	set data(value:Array<{nom:string, heure:string, id:number, date:string}>):void {
		this.#data = value;
		this.children = this.#data.map(evenements => new Evenement(evenements));
	}

	mount(events:HTMLElement):void {
		fetch( 'http://localhost:8080/api/v1/events', {
			method:'GET',
			headers: { 'Content-Type': 'application/json' },
		})
		.then( (response:Response) => response.json() )
		.then( (data:any) => {
				this.data = data;
				events.innerHTML = this.render();
				// $FlowFixMe
				this.submit = this.submit.bind(this);
				$('form.Evenement').submit( this.submit );
				$('input').attr('value','Réserver');
		});

	}
	
	// Lien personnalisé selon l'evenement : on recupère le date/nom pour réserver
	submit(event:Event):void {
		let nbElements = $( 'form.Evenement' ).length;
		console.log(nbElements);
		let terminaison = new Array();
		for(let i = 1; i <= nbElements; i++) {
			terminaison[i-1] = $(event.currentTarget).parent().children(`:nth-child(${i})`).children(':first-child').attr('class');
		}
		let c = 0;
		c = $(event.currentTarget).index();
		event.preventDefault();
            // à modifier
			fetch( `http://localhost:8080/api/v1/${terminaison[c]}`, {
					method:'POST',
					headers: { 'Content-Type': 'application/json' },
					body: JSON.stringify(utilisateur)
				})
			.then(response => {
				if (!response.ok) {
					throw new Error( `${response.status} : ${response.statusText}` );
				}
				return response.json();
			})
			.then ( newUser => {
                alert(`Utilisateur "${newUser.login}" enregistrée avec succès ! (id ${newUser.id})`);
				// puis on vide le formulaire
				const form:?HTMLElement = document.querySelector('form.InscriptionPage');
				if (form && form instanceof HTMLFormElement) {
					form.reset();
				}
			})
			.catch( error => alert(`Enregistrement impossible : ${error.message}`) );

	}
}
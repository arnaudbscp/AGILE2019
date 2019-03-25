import Page from './Page.js';
import $ from 'jquery';

export default class Connexion extends Page {
	constructor(){
		super();
		super('Se connecter');
		// $FlowFixMe
		this.submit = this.submit.bind(this);
	}

	render():string {
        return `<form class="Connexion">
        <div class="form-group">
          <label for="exampleInputEmail1">Login</label>
          <input type="text" name='login' class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email">
          <small id="emailHelp" class="form-text text-muted">Attention : Vous ne devrez jamais partager votre e-mail avec quelqu'un d'autre.</small>
        </div>
        <div class="form-group">
          <label for="exampleInputPassword1">Password</label>
          <input type="password" name='password' class="form-control" id="exampleInputPassword1" placeholder="Password">
        </div>
        <button type="submit" class="btn btn-primary">Se connecter</button>
      </form>`
	}

	mount(container:HTMLElement):void {
		$('form.Connexion').submit( this.submit );
	}

	submit(event:Event):void {
		event.preventDefault();
		const fieldNames:Array<string> = [
			'login',
			'password'
		];
		// on récupère la valeur saisie dans chaque champ
		const values:any = {};
		const errors:Array<string> = [];

		fieldNames.forEach( (fieldName:string) => {
			const value = this.getFieldValue(fieldName);
			if ( !value ){
				errors.push( `Le champ ${fieldName} ne peut être vide !` );
			}
			values[fieldName] = value;
		});

		if (errors.length) {
			// si des erreurs sont détectées, on les affiche
			alert( errors.join('\n') );
		} else {
			// si il n'y a pas d'erreur on envoie les données
			const utilisateur = {
				login : values.login,
				password : values.password
			};
			fetch( '/api/v1/users', {
					method:'GET',
					//headers: { 'Content-Type': 'application/json' },
					//body: JSON.stringify(utilisateur)
				})
			.then(response => {
				if (!response.ok) {
					throw new Error( `${response.status} : ${response.statusText}` );
				}
				return response.json();
			})
			.then ( newUtilisateur => {
				let p = 0;
				let pseudo = "";
				for(let i = 0; i < newUtilisateur.length; i++) {
					if(newUtilisateur[i].email == utilisateur.login) {
						p = p + 1;
						pseudo = newUtilisateur[i].login;
						if(newUtilisateur[i].role == "admin") {
							p = p + 1;
						}
					}
					if(newUtilisateur[i].password == utilisateur.password) {
						p = p + 1;
					}

				}
				console.log(p);
				if(p == 2) {
	    			var d = new Date;
	    			d.setTime(d.getTime() + 24*60*60*1000*-1);
	    			document.cookie = "username" + "=" + "" + ";path=/;expires=" + d.toGMTString();
					alert(`Utilisateur "${utilisateur.login}" s'est connecté avec succès ! Réservation possible.`);		
					document.cookie = `username=${pseudo};`;
					p= 0;
				}else if(p == 3) {
					var d = new Date;
	    			d.setTime(d.getTime() + 24*60*60*1000*-1);
	    			document.cookie = "username" + "=" + "" + ";path=/;expires=" + d.toGMTString();
					alert(`L'admin "${utilisateur.login}" s'est connecté avec succès`);		
					document.cookie = `username=admin;`;
					p = 0;
					window.location.reload();
					document.querySelector("li:nth-child(2)").innerHTML = "";
					document.querySelector("li:nth-child(3)").innerHTML = "";
					console.log(document.querySelector("ul:first-child").innerHTML);
				}else {
					alert(`Mauvaise connexion`);
				}
				// puis on vide le formulaire
				// const form:?HTMLElement = document.querySelector('form.connexion');
				// if (form && form instanceof HTMLFormElement) {
				// 	form.reset();
				// }
			}) 
			.catch( error => alert(`Connnexion impossible : ${error.message}`) );
		}
	}

	getFieldValue(fieldName:string):?string|Array<string>{
		// on récupère une référence vers le champ qui a comme attribut `name` la valeur fieldName (nom, base, prix_petite, etc.)
		const field:?HTMLElement = document.querySelector(`[name=${fieldName}]`);
		if ( field instanceof HTMLInputElement ) {
			// s'il s'agit d'un <input> on utilise la propriété `value`
			// et on retourne la chaine de caractère saisie
			return field.value != '' ? field.value : null;
		} else if ( field instanceof HTMLSelectElement ) {
			// s'il s'agit d'un <select> on utilise la propriété `selectedOptions`
			const values:Array<string> = [];
			for (let i = 0; i < field.selectedOptions.length; i++) {
				values.push( field.selectedOptions[i].value );
			}
			// et on retourne un tableau avec les valeurs sélectionnées
			return values.length ? values : null;
		}
		return null;
	}
}
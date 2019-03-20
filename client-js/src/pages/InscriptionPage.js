// @flow
import Page from './Page.js';
import $ from 'jquery';
import { StringDecoder } from 'string_decoder';

export default class InscriptionPage extends Page {
	constructor(){
		super('S\'inscire');
		// $FlowFixMe
		this.submit = this.submit.bind(this);
	}

	render():string {
		return `<form class="InscriptionPage">
        <div class="form-row">
        <div class="col-md-4 mb-3">
          <label for="validationDefault01">First name</label>
          <input type="text" class="form-control" id="login" name="login" placeholder="First name" value="Mark" required>
        </div>
        <div class="col-md-4 mb-3">
          <label for="validationDefault02">Last name</label>
          <input type="text" class="form-control" id="validationDefault02" name="id" placeholder="Last name" value="Otto" >
        </div>
        <div class="col-md-4 mb-3">
          <label for="validationDefaultUsername">Username</label>
          <div class="input-group">
            <div class="input-group-prepend">
              <span class="input-group-text" id="inputGroupPrepend2">@</span>
            </div>
            <input type="text" class="form-control" id="email" name="email" placeholder="Username" aria-describedby="inputGroupPrepend2" required>
          </div>
        </div>
      </div>
      <div class="form-row">
        <div class="col-md-6 mb-3">
          <label for="validationDefault03">Mot de passe</label>
          <input type="text" class="form-control" id="password" name="password" placeholder="Mot de passe" required>
        </div>
        <div class="col-md-3 mb-3">
          <label for="validationDefault04">State</label>
          <input type="text" class="form-control" id="validationDefault04" placeholder="State" >
        </div>
        <div class="col-md-3 mb-3">
          <label for="validationDefault05">Zip</label>
          <input type="text" class="form-control" id="validationDefault05" placeholder="Zip" >
        </div>
      </div>
      <div class="form-group">
        <div class="form-check">
          <input class="form-check-input" type="checkbox" value="" id="invalidCheck2" required>
          <label class="form-check-label" for="invalidCheck2">
            Agree to terms and conditions
          </label>
        </div>
      </div>
		<button type="submit" class="btn btn-default">Inscription</button>
	</form>`;
	}

	mount(container:HTMLElement):void {
		$('form.InscriptionPage').submit( this.submit );
	}

	submit(event:Event):void {
		event.preventDefault();
		const fieldNames:Array<string> = [
			'login',
			'password',
			'email'
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
				login: values.login,
				password: values.password,
				email: values.email
         };
            //modifier
			fetch( '/api/v1/users', {
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
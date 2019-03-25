import Page from './Page.js';
import $ from 'jquery';

export default class Admin extends Page {
	constructor(){
		super('Administration');
  }

   render():string {
    let cc = "";
    var name = "username" + "=";
      var decodedCookie = decodeURIComponent(document.cookie);
      var ca = decodedCookie.split(';');
      console.log(document.cookie);
      for(var i = 0; i <ca.length; i++) {
        var ccc = ca[i];
        while (ccc.charAt(0) == ' ') {
              ccc = ccc.substring(1);
        }
        if (ccc.indexOf(name) == 0) {
              cc = ccc.substring(name.length, ccc.length);
        }
      }
      if(cc == 'admin') {
          return `
          <form class="ajouter">
          <div class="form-group">
          <label for="exampleFormControlInput1">Titre evenement</label>
          <input type="text" class="form-control" id="exampleFormControlInput1" name="nom" placeholder="Titre">
          </div>
          <div class="form-group">
          <label for="exampleFormControlInput1">Date aaaa-mm-jj</label>
          <input type="text" class="form-control" id="exampleFormControlInput1" name="date" placeholder="2019-04-22">
          </div>
          <div class="form-group">
          <label for="exampleFormControlInput1">Nombre de places disponibles</label>
          <input type="number" class="form-control" id="exampleFormControlInput1" name="place" placeholder="5">
          </div>
          <div class="form-group">
          <label for="exampleFormControlInput1">Prix</label>
          <input type="number" class="form-control" id="exampleFormControlInput1" name="prix" placeholder="45">
          </div>
          <div class="form-group">
          <label for="exampleFormControlInput1">Heure de départ</label>
          <input type="text" class="form-control" id="exampleFormControlInput1" name="heure" placeholder="10:30:00">
          </div>
          <div class="form-group">
          <label for="exampleFormControlInput1">Heure de fin</label>
          <input type="text" class="form-control" id="exampleFormControlInput1" name="heureFin" placeholder="12:30:00">
          </div>
          <div class="form-group">
          <label for="exampleFormControlTextarea1">Description</label>
          <textarea class="form-control" name="description" id="exampleFormControlTextarea1" rows="3"></textarea>
          </div>
          <button type="submit" class="btn btn-primary mb-2">Créer</button>
          </form>
          <hr class="my-4">
          <form class="supprimer">
          <div class="form-group">
          <label for="exampleFormControlInput1">Titre evenement</label>
          <input type="text" class="form-control" id="exampleFormControlInput1" name="nomd" placeholder="Titre de l'article à supprimer">
          </div>
          <div class="form-group">
          <label for="exampleFormControlInput1">Jour de l'evenement</label>
          <input type="text" class="form-control" id="exampleFormControlInput1" name="dated" placeholder="2019-04-22">
          </div>
          <button type="submit" class="btn btn-primary mb-2">Supprimer</button>
          </form>
          <hr class="my-4">
          <form class="moderer">
          <div class="form-group">
          <label for="exampleFormControlInput1">Login utilisateur à supprimer</label>
          <input type="text" class="form-control" id="exampleFormControlInput1" name="nomt" placeholder="bernard">
          </div>
          <button type="submit" class="btn btn-primary mb-2">Supprimer</button>
          </form>
          `;
      }else if(cc.length > 0 ) {
        return "<h4>Mes prochains evenements :</h4><br/><form class='evenements'><button type=\"submit\" class=\"btn btn-primary mb-2\">Afficher</button></form>";
      }else{
        return "vous n'avez pas les droits pour accéder à cette page";
      }
    }
      mount(container:HTMLElement):void {
        console.log("coucou");
        $('form.ajouter').submit( this.submit );
        $('form.supprimer').submit( this.submitdeux );
        $('form.moderer').submit( this.submittrois );
        //$('.evenements').submit(this.submittrois);
      }

      submit(event:Event):void {
        event.preventDefault();
        const fieldNames:Array<string> = [
          'date',
          'description',
          'heure',
          'heureFin',
          'nom',
          'place',
          'prix'
        ];
        // on récupère la valeur saisie dans chaque champ
        const values:any = {};
        const errors:Array<string> = [];
    
        fieldNames.forEach( (fieldName:string) => {
          let value = "";
          // on récupère une référence vers le champ qui a comme attribut `name` la valeur fieldName (nom, base, prix_petite, etc.)
          const field:?HTMLElement = document.querySelector(`[name=${fieldName}]`);
          if ( field instanceof HTMLInputElement ) {
            // s'il s'agit d'un <input> on utilise la propriété `value`
            // et on retourne la chaine de caractère saisie
            value = field.value != '' ? field.value : null;
          } else if ( field instanceof HTMLSelectElement ) {
            // s'il s'agit d'un <select> on utilise la propriété `selectedOptions`
            const values:Array<string> = [];
            for (let i = 0; i < field.selectedOptions.length; i++) {
              values.push( field.selectedOptions[i].value );
            }
          }
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
          const evenement = {
            date: values.date,
            description: values.description,
            heure: values.heure,
            heureFin: values.heureFin,
            nom: values.nom,
            place: values.place,
            prix: values.prix
             };
                //modifier
          fetch( '/api/v1/events/', {
              method:'POST',
              headers: { 'Content-Type': 'application/json' },
              body: JSON.stringify(evenement)
            })
          .then(response => {
            if (!response.ok) {
              throw new Error( `${response.status} : ${response.statusText}` );
            }
            return response.json();
          })
          .then ( e => {
            alert(`L'evenement "${e.nom}" enregistré avec succès !`);
            // puis on vide le formulaire
            const form:?HTMLElement = document.querySelector('form.ajouter');
            if (form && form instanceof HTMLFormElement) {
              form.reset();
            }
          })
          .catch( error => alert(`Enregistrement impossible : ${error.message}`) );
        }
      }

      submitdeux(event:Event):void {
        event.preventDefault();
        const fieldNames:Array<string> = [
          'nomd',
          'dated',
        ];
        // on récupère la valeur saisie dans chaque champ
        const values:any = {};
        const errors:Array<string> = [];
    
        fieldNames.forEach( (fieldName:string) => {
          let value = "";
          // on récupère une référence vers le champ qui a comme attribut `name` la valeur fieldName (nom, base, prix_petite, etc.)
          const field:?HTMLElement = document.querySelector(`[name=${fieldName}]`);
          if ( field instanceof HTMLInputElement ) {
            // s'il s'agit d'un <input> on utilise la propriété `value`
            // et on retourne la chaine de caractère saisie
            value = field.value != '' ? field.value : null;
          } else if ( field instanceof HTMLSelectElement ) {
            // s'il s'agit d'un <select> on utilise la propriété `selectedOptions`
            const values:Array<string> = [];
            for (let i = 0; i < field.selectedOptions.length; i++) {
              values.push( field.selectedOptions[i].value );
            }
          }
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
          const evenement = {
            dated: values.dated,
            nomd: values.nomd,
             };
                //modifier
          fetch( `/api/v1/events/${evenement.nomd}/${evenement.dated}/`, {
              method:'DELETE',
              //headers: { 'Content-Type': 'application/json' }
            })
          .then(response => {
            if (!response.ok) {
              throw new Error( `${response.status} : ${response.statusText}` );
            }
            return response;
          })
          .then ( e => {
            alert(`La suppression est envoyée !`);
            // puis on vide le formulaire
            const form:?HTMLElement = document.querySelector('form.supprimer');
            if (form && form instanceof HTMLFormElement) {
              form.reset();
            }
          })
          .catch( error => alert(`Enregistrement impossible : ${error.message}`) );
        }
      }

      submittrois(event:Event):void {
        event.preventDefault();
        const fieldNames:Array<string> = [
          'nomt',
        ];
        // on récupère la valeur saisie dans chaque champ
        const values:any = {};
        const errors:Array<string> = [];
    
        fieldNames.forEach( (fieldName:string) => {
          let value = "";
          // on récupère une référence vers le champ qui a comme attribut `name` la valeur fieldName (nom, base, prix_petite, etc.)
          const field:?HTMLElement = document.querySelector(`[name=${fieldName}]`);
          if ( field instanceof HTMLInputElement ) {
            // s'il s'agit d'un <input> on utilise la propriété `value`
            // et on retourne la chaine de caractère saisie
            value = field.value != '' ? field.value : null;
          } else if ( field instanceof HTMLSelectElement ) {
            // s'il s'agit d'un <select> on utilise la propriété `selectedOptions`
            const values:Array<string> = [];
            for (let i = 0; i < field.selectedOptions.length; i++) {
              values.push( field.selectedOptions[i].value );
            }
          }
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
          const evenement = {
            nomt: values.nomt,
             };
                //modifier
          fetch( `/api/v1/users/${evenement.nomt}/`, {
              method:'DELETE',
              //headers: { 'Content-Type': 'application/json' }
            })
          .then(response => {
            if (!response.ok) {
              throw new Error( `${response.status} : ${response.statusText}` );
            }
           // return response;
          })
          .then ( e => {
            alert(`La suppression est envoyée !`);
            // puis on vide le formulaire
            const form:?HTMLElement = document.querySelector('form.moderer');
            if (form && form instanceof HTMLFormElement) {
              form.reset();
            }
          })
          .catch( error => alert(`Enregistrement impossible : ${error.message}`) );
        }
      }
}

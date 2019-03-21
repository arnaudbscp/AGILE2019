import Page from './Page.js';
import $ from 'jquery';

export default class Connexion extends Page {
	constructor(){
		super();
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
          <input type="text" class="form-control" id="exampleFormControlInput1" name="titre" placeholder="Titre">
        </div>
        <div class="form-group">
        <label for="exampleFormControlInput1">Date jj/mm/aaaa</label>
        <input type="text" class="form-control" id="exampleFormControlInput1" name="jour" placeholder="00/00/0000">
      </div>
  <div class="form-group">
    <label for="exampleFormControlSelect1">Créneau</label>
    <select class="form-control" name="creneau" id="exampleFormControlSelect1">
      <option>8h12h</option>
      <option>14h-17h</option>
    </select>
  <div class="form-group">
    <label for="exampleFormControlTextarea1">Description</label>
    <textarea class="form-control" name="description" id="exampleFormControlTextarea1" rows="3"></textarea>
  </div>
  <button type="submit" class="btn btn-primary mb-2">Créer</button>
</form>
          `;
          // submission
      }else {
          return "vous n'avez pas les droits pour accéder à cette page";
      }
    }

}
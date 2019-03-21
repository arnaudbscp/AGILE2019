// @flow
import Component from './Component.js';
import $ from 'jquery';

export default class Evenement extends Component {
	constructor(evenement:{nom:string, heure:string, id:number, date:string}){
		super('form', {name:'class', value:'Evenement'}, [
				new Component('section', {name:'class', value: `${evenement.nom}`}, [
					new Component('h4', null, evenement.nom),
					new Component( 'ul', null, [
						new Component('p', null, `Date : ${evenement.date}`),
                        new Component('li', null, `Heure : ${evenement.heure}`),
                        new Component('input', {name:'type', value: "submit"})
					])
				])	
        ]);
    }

}

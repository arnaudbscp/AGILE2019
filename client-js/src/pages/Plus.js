// @flow
import Page from './Page.js';
import $ from 'jquery';

export default class Plus extends Page {
	constructor(){
		super('En savoir plus');
		// $FlowFixMe
	}

	render():string {
		return `
		<section class="jumbotron text-center">
        <div class="container">
          <h1 class="jumbotron-heading">Qui suis-je ?</h1>
          <p class="lead text-muted">Bonjour, moi c’est Lucile Grimaud, j’ai 27 ans et je suis la fondatrice de l’entreprise Ramponno. J’ai étudié pendant 5 ans la tapisserie d’ameublement à l’école Boulle de Paris pour être diplômée dans les métiers d’art de tapisserie décoration. A la fin de mes études, j’ai travaillé en tant que prototypiste et tapissier polyvalent pour ensuite me décider à créer ma propre entreprise. Mon but à travers ce projet est de faire découvrir mon métier d’art au grand public en travaillant avec des fournisseurs locaux. Ainsi, Ramponno est né il y a un peu plus d’un an.</p>
        </div>
		  </section>
		  <section class="jumbotron text-center">
		  <div class="container">
			<h1 class="jumbotron-heading">Qu'est-ce que Ramponno ?</h1>
			<p class="lead text-muted">
			Le nom de la société fait référence au Ramponneau, qui est le marteau incontournable de la tapisserie. Ainsi, Ramponno c’est une occasion unique de se mettre dans la peau d’un tapissier le temps de rénover ou de créer un siège ! Pour se faire, vous avez le choix entre plusieurs services :<br/>
Les cours de tapisserie : pour apprendre des techniques de tapisserie sur le projet de chacun pour créer des assises<br/>
Les « jeudis tapisserie » : permet la mise à disposition de l’atelier et de ses outils pour créer en autonomie des projets<br/>
Les stages : 4 jours pour réaliser un fauteuil et découvrir le savoir-faire de l’artisan tapissier<br/>
Les ateliers thématiques : sur une après-midi ou une journée pour apprendre une technique de la tapisserie en réalisant un objet <br/>
La location d’outils <br/>
Je vous invite donc à venir me rencontrer pour découvrir mon univers et pour travailler dans une ambiance musicale et chaleureuse permise par la mise en avant de mes valeurs, telles que le partage de mon savoir-faire ou le respect de l’environnement.
			</p>
		  </div>
			</section>

  `;	}
}
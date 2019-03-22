// @flow
import Page from './Page.js';
import $ from 'jquery';

export default class Plus extends Page {
	constructor(){
		super('En savoir plus');
		// $FlowFixMe
	}

	render():string {
		return `<h3>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut tincidunt accumsan libero in commodo. Ut vel lorem orci. Suspendisse potenti. Sed porta, augue vitae cursus iaculis, velit ligula lobortis sem, at consequat leo velit ut tortor. Aliquam neque mi, luctus eu eros id, facilisis commodo risus. Donec ac nisi risus. Nullam ullamcorper volutpat eros, pretium fringilla turpis semper nec. Vivamus turpis nibh, dignissim vitae mi ut, aliquam scelerisque magna. Maecenas rhoncus ex at sem vestibulum, a iaculis dolor ultrices. Proin volutpat, magna nec convallis fringilla, justo eros hendrerit erat, eget dapibus elit lectus in orci. Aenean ultrices rutrum consectetur. Suspendisse sed interdum felis. Ut eu euismod ipsum. Fusce mollis ante eu magna semper posuere. Praesent erat nisl, faucibus at purus ut, ornare posuere dui. Nulla cursus blandit semper.</h3>`;
	}
}
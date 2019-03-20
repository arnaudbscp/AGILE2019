// @flow
import Page from './pages/Page.js';

export default class PageRenderer {

	static titleElement:?HTMLElement;
	static contentElement:?HTMLElement;

	static renderPage( page:Page ):void {
		if (this.titleElement){
			// $FlowFixMe
			this.titleElement.innerHTML = page.renderTitle();
		}
		if (this.contentElement){
			// $FlowFixMe
			this.contentElement.innerHTML = page.render();
			// $FlowFixMe
			page.mount(this.contentElement);
		}
	}
}

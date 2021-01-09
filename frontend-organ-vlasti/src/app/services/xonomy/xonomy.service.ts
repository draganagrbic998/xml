import { Injectable } from '@angular/core';

declare const Xonomy: any;

@Injectable({
  providedIn: 'root'
})
export class XonomyService {

  constructor() { }

  detaljiSpecifikacija = {
    elements: {
      Detalji: {
        hasText: true,
        menu: [
          {
            caption: 'Dodaj <bold> tag',
            action: Xonomy.newElementChild,
            actionParameter: '<bold></bold>'
          },
          {
            caption: 'Dodaj <italic> tag',
            action: Xonomy.newElementChild,
            actionParameter: '<italic></italic>'
          }
        ]
      },
      bold: {
        hasText: true,
        menu: [
          {
            caption: 'Obriši <bold> tag',
            action: Xonomy.deleteElement
          }
        ]
      },
      italic: {
        hasText: true,
        menu: [
          {
            caption: 'Obriši <italic> tag',
            action: Xonomy.deleteElement
          }
        ]
      }
    }
  };

  removeXmlSpace(xml: string): string{
    const parser = new DOMParser();
    const serializer = new XMLSerializer();
    const document = parser.parseFromString(xml, 'text/xml');

    const detalji = document.getElementsByTagName('Detalji');
    for (let i = 0; i < detalji.length; ++i){
      detalji.item(i).removeAttribute('xml:space');
    }

    const bolds = document.getElementsByTagName('bold');
    for (let i = 0; i < bolds.length; ++i){
      bolds.item(i).removeAttribute('xml:space');
    }

    const italics = document.getElementsByTagName('italic');
    for (let i = 0; i < italics.length; ++i){
      italics.item(i).removeAttribute('xml:space');
    }

    return serializer.serializeToString(document);
  }

}

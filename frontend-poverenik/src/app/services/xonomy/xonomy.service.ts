import { Injectable } from '@angular/core';

declare const Xonomy: any;

@Injectable({
  providedIn: 'root'
})
export class XonomyService {

  constructor() { }

  public odlukaSpecifikacija = {
    elements: {
      Dispozitiva: {
        menu: [
          {
            caption: 'Dodaj <Pasus> tag',
            action: Xonomy.newElementChild,
            actionParameter: '<Pasus></Pasus>'
          }
        ]
      },
      Obrazlozenje: {
        menu: [
          {
            caption: 'Dodaj <Pasus> tag',
            action: Xonomy.newElementChild,
            actionParameter: '<Pasus></Pasus>'
          }
        ]
      },
      Pasus: {
        hasText: true,
        menu: [
          {
            caption: 'Obriši <Pasus> tag',
            action: Xonomy.deleteElement
          },
          {
            caption: 'Dodaj <zakon> tag',
            action: Xonomy.newElementChild,
            actionParameter: '<zakon></zakon>'
          }
        ]
      },
      zakon: {
        hasText: true,
        menu: [
          {
            caption: 'Obriši <zakon> tag',
            action: Xonomy.deleteElement
          }
        ]
      }
    }
  };

  public detaljiSpecifikacija = {
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

    const bolds = document.getElementsByTagName('bold');
    for (let i = 0; i < bolds.length; ++i){
      bolds.item(i).removeAttribute('xml:space');
    }
    const italics = document.getElementsByTagName('italic');
    for (let i = 0; i < italics.length; ++i){
      italics.item(i).removeAttribute('xml:space');
    }
    const detalji = document.getElementsByTagName('Detalji');
    for (let i = 0; i < detalji.length; ++i){
      detalji.item(i).removeAttribute('xml:space');
    }
    const zakoni = document.getElementsByTagName('zakon');
    for (let i = 0; i < zakoni.length; ++i){
      zakoni.item(i).removeAttribute('xml:space');
    }
    const pasusi = document.getElementsByTagName('Pasus');
    for (let i = 0; i < pasusi.length; ++i){
      pasusi.item(i).removeAttribute('xml:space');
    }
    const odluke = document.getElementsByTagName('Odluka');
    for (let i = 0; i < odluke.length; ++i){
      odluke.item(i).removeAttribute('xml:space');
    }
    const dispozitive = document.getElementsByTagName('Dispozitiva');
    for (let i = 0; i < dispozitive.length; ++i){
      dispozitive.item(i).removeAttribute('xml:space');
    }
    const obrazlozenja = document.getElementsByTagName('Obrazlozenje');
    for (let i = 0; i < obrazlozenja.length; ++i){
      obrazlozenja.item(i).removeAttribute('xml:space');
    }

    return serializer.serializeToString(document);
  }

}

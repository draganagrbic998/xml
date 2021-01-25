import { Injectable } from '@angular/core';
import { RESENJE } from 'src/app/constants/namespaces';

declare const Xonomy: any;

@Injectable({
  providedIn: 'root'
})
export class XonomyService {

  constructor() { }

  private baseMenu = [
    {
      caption: 'Dodaj <datum> tag',
      action: Xonomy.newElementChild,
      actionParameter: '<datum></datum>',
      hideIf: (jsElement) => {
        return this.hideMetapodatak(jsElement, 'datum');
      },
    },
    {
      caption: 'Dodaj <organVlasti> tag',
      action: Xonomy.newElementChild,
      actionParameter: '<organVlasti></organVlasti>',
      hideIf: (jsElement) => {
        return this.hideMetapodatak(jsElement, 'organVlasti');
      },
    },
    {
      caption: 'Dodaj <izdatoU> tag',
      action: Xonomy.newElementChild,
      actionParameter: '<izdatoU></izdatoU>',
      hideIf: (jsElement) => {
        return this.hideMetapodatak(jsElement, 'izdatoU');
      },
    },
    {
      caption: 'Dodaj <and> tag',
      action: Xonomy.newElementChild,
      actionParameter: '<and></and>',
      hideIf: (jsElement) => {
        return this.hideLogOp(jsElement);
      },
    },
    {
      caption: 'Dodaj <or> tag',
      action: Xonomy.newElementChild,
      actionParameter: '<or></or>',
      hideIf: (jsElement) => {
        return this.hideLogOp(jsElement);
      },
    }
  ];

  private extendedMenu = [...this.baseMenu, ...[
    {
      caption: 'Dodaj <mesto> tag',
      action: Xonomy.newElementChild,
      actionParameter: '<mesto></mesto>',
      hideIf: (jsElement) => {
        return this.hideMetapodatak(jsElement, 'mesto');
      },
    }
  ]];

  zalbaPretraga = {
    elements: {
      datum: {
        hasText: true
      },
      organVlasti: {
        hasText: true
      },
      izdatoU: {
        hasText: true
      },
      mesto: {
        hasText: true
      },
      tip: {
        hasText: true,
        asker: Xonomy.askPicklist,
        askerParameter: ['cutanje', 'odbijanje', 'delimicnost']
      },
      stanje: {
        hasText: true,
        asker: Xonomy.askPicklist,
        askerParameter: ['cekanje', 'prosledjeno', 'odgovoreno', 'odustato', 'obavesteno', 'odobreno', 'odbijeno']
      },
      Pretraga: {
        menu: [...this.extendedMenu, ...[
          {
            caption: 'Dodaj <tip> tag',
            action: Xonomy.newElementChild,
            actionParameter: '<tip></tip>',
            hideIf: (jsElement) => {
              return this.hideMetapodatak(jsElement, 'tip');
            },
          },
          {
            caption: 'Dodaj <stanje> tag',
            action: Xonomy.newElementChild,
            actionParameter: '<stanje></stanje>',
            hideIf: (jsElement) => {
              return this.hideMetapodatak(jsElement, 'stanje');
            },
          }
        ]]
      }
    }
  };

  odgovorPretraga = {
    elements: {
      datum: {
        hasText: true
      },
      organVlasti: {
        hasText: true
      },
      izdatoU: {
        hasText: true
      },
      Pretraga: {
        menu: this.baseMenu
      }
    }
  };

  resenjePretraga = {
    elements: {
      datum: {
        hasText: true
      },
      organVlasti: {
        hasText: true
      },
      izdatoU: {
        hasText: true
      },
      status: {
        hasText: true,
        asker: Xonomy.askPicklist,
        askerParameter: ['odustato', 'obavesteno', 'odobreno', 'odbijeno']
      },
      tipZalbe: {
        hasText: true,
        asker: Xonomy.askPicklist,
        askerParameter: ['cutanje', 'odbijanje', 'delimicnost']
      },
      Pretraga: {
        menu: [...this.baseMenu, ...[
          {
            caption: 'Dodaj <status> tag',
            action: Xonomy.newElementChild,
            actionParameter: '<status></status>',
            hideIf: (jsElement) => {
              return this.hideMetapodatak(jsElement, 'status');
            },
          },
          {
            caption: 'Dodaj <tipZalbe> tag',
            action: Xonomy.newElementChild,
            actionParameter: '<tipZalbe></tipZalbe>',
            hideIf: (jsElement) => {
              return this.hideMetapodatak(jsElement, 'tipZalbe');
            },
          }
        ]]
      }
    }
  };

  izvestajPretraga = {
    elements: {
      datum: {
        hasText: true
      },
      organVlasti: {
        hasText: true
      },
      izdatoU: {
        hasText: true
      },
      godina: {
        hasText: true
      },
      Pretraga: {
        menu: [...this.baseMenu, ...[
          {
            caption: 'Dodaj <godina> tag',
            action: Xonomy.newElementChild,
            actionParameter: '<godina></godina>',
            hideIf: (jsElement) => {
              return this.hideMetapodatak(jsElement, 'godina');
            },
          }
        ]]
      }
    }
  };

  obicnaPretraga = {
    elements: {
      Fraze: {
        menu: [
          {
            caption: 'Dodaj <fraza> tag',
            action: Xonomy.newElementChild,
            actionParameter: '<fraza></fraza>'
          }
        ]
      },
      fraza: {
        hasText: true,
        asker: Xonomy.askString,
        menu: [
          {
            caption: 'Obriši <fraza> tag',
            action: Xonomy.deleteElement
          }
        ]
      },
      kljucne_reci: {
        hasText: true
      }
    }
  };

  odlukaSpecifikacija = {
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
      zakoni.item(i).setAttribute('xmlns', RESENJE);
    }
    const pasusi = document.getElementsByTagName('Pasus');
    for (let i = 0; i < pasusi.length; ++i){
      pasusi.item(i).removeAttribute('xml:space');
      pasusi.item(i).setAttribute('xmlns', RESENJE);
    }
    const dispozitive = document.getElementsByTagName('Dispozitiva');
    for (let i = 0; i < dispozitive.length; ++i){
      dispozitive.item(i).removeAttribute('xml:space');
      dispozitive.item(i).setAttribute('xmlns', RESENJE);
    }
    const obrazlozenja = document.getElementsByTagName('Obrazlozenje');
    for (let i = 0; i < obrazlozenja.length; ++i){
      obrazlozenja.item(i).removeAttribute('xml:space');
      obrazlozenja.item(i).setAttribute('xmlns', RESENJE);
    }
    const odluke = document.getElementsByTagName('Odluka');
    for (let i = 0; i < odluke.length; ++i){
      odluke.item(i).removeAttribute('xml:space');
      odluke.item(i).setAttribute('xmlns', RESENJE);
    }

    return serializer.serializeToString(document);
  }

  hideMetapodatak(jsElement, metapodatak: string): boolean{
    if (jsElement.children.length === 0){
      return false;
    }
    if (jsElement.hasChildElement(metapodatak)){
      return true;
    }
    const prev = jsElement.children[jsElement.children.length - 1];
    if (prev.name !== 'and' && prev.name !== 'or'){
      return true;
    }
    return false;
  }

  hideLogOp(jsElement): boolean{
    if (jsElement.children.length === 0){
      return true;
    }
    const prev = jsElement.children[jsElement.children.length - 1];
    if (prev.name === 'and' || prev.name === 'or'){
      return true;
    }
    return false;
  }

}

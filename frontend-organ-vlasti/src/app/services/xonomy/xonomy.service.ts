import { Injectable } from '@angular/core';

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
      hideIf: (jsElement) => this.hideMetapodatak(jsElement, 'datum'),
    },
    {
      caption: 'Dodaj <organVlasti> tag',
      action: Xonomy.newElementChild,
      actionParameter: '<organVlasti></organVlasti>',
      hideIf: (jsElement) => this.hideMetapodatak(jsElement, 'organVlasti'),
    },
    {
      caption: 'Dodaj <izdatoU> tag',
      action: Xonomy.newElementChild,
      actionParameter: '<izdatoU></izdatoU>',
      hideIf: (jsElement) => this.hideMetapodatak(jsElement, 'izdatoU'),
    },
    {
      caption: 'Dodaj <and> tag',
      action: Xonomy.newElementChild,
      actionParameter: '<and></and>',
      hideIf: (jsElement) => this.hideLogOp(jsElement),
    },
    {
      caption: 'Dodaj <or> tag',
      action: Xonomy.newElementChild,
      actionParameter: '<or></or>',
      hideIf: (jsElement) => this.hideLogOp(jsElement),
    }
  ];

  private extendedMenu = [...this.baseMenu, ...[
    {
      caption: 'Dodaj <mesto> tag',
      action: Xonomy.newElementChild,
      actionParameter: '<mesto></mesto>',
      hideIf: (jsElement) => this.hideMetapodatak(jsElement, 'mesto'),
    }
  ]];

  private baseChild = {
    hasText: true,
    menu: [{
      caption: 'Add @not attribute',
      action: Xonomy.newAttribute,
      actionParameter: { name: 'not', value: 'true' },
      hideIf: (jsElement) => jsElement.hasAttribute('not')
    }],
    attributes: {
      not: {
        menu: [{
          caption: 'Delete @not attribute',
          action: Xonomy.deleteAttribute
        }]
      }
    }
  };

  zahtevPretraga = {
    elements: {
      datum: this.baseChild,
      organVlasti: this.baseChild,
      izdatoU: this.baseChild,
      mesto: this.baseChild,
      tip: {...this.baseChild, ...{
        hasText: true,
        asker: Xonomy.askPicklist,
        askerParameter: ['obavestenje', 'uvid', 'kopija', 'dostava']
      }},
      Pretraga: {
        menu: [...this.extendedMenu, ...[
          {
            caption: 'Dodaj <tip> tag',
            action: Xonomy.newElementChild,
            actionParameter: '<tip></tip>',
            hideIf: (jsElement) => this.hideMetapodatak(jsElement, 'tip'),
          }
        ]]
      }
    }
  };

  odlukaPretraga = {
    elements: {
      datum: this.baseChild,
      organVlasti: this.baseChild,
      izdatoU: this.baseChild,
      mesto: this.baseChild,
      tip: {...this.baseChild, ...{
        hasText: true,
        asker: Xonomy.askPicklist,
        askerParameter: ['obavestenje', 'odbijanje']
      }},
      Pretraga: {
        menu: [...this.extendedMenu, ...[
          {
            caption: 'Dodaj <tip> tag',
            action: Xonomy.newElementChild,
            actionParameter: '<tip></tip>',
            hideIf: (jsElement) => this.hideMetapodatak(jsElement, 'tip'),
          }
        ]]
      }
    }
  };

  zalbaPretraga = {
    elements: {
      datum: this.baseChild,
      organVlasti: this.baseChild,
      izdatoU: this.baseChild,
      mesto: this.baseChild,
      tip: {...this.baseChild, ...{
        hasText: true,
        asker: Xonomy.askPicklist,
        askerParameter: ['cutanje', 'odbijanje', 'delimicnost']
      }},
      stanje: {...this.baseChild, ...{
        hasText: true,
        asker: Xonomy.askPicklist,
        askerParameter: ['cekanje', 'prosledjeno', 'odgovoreno', 'odustato', 'obavesteno', 'odobreno', 'odbijeno']
      }},
      Pretraga: {
        menu: [...this.extendedMenu, ...[
          {
            caption: 'Dodaj <tip> tag',
            action: Xonomy.newElementChild,
            actionParameter: '<tip></tip>',
            hideIf: (jsElement) => this.hideMetapodatak(jsElement, 'tip'),
          },
          {
            caption: 'Dodaj <stanje> tag',
            action: Xonomy.newElementChild,
            actionParameter: '<stanje></stanje>',
            hideIf: (jsElement) => this.hideMetapodatak(jsElement, 'stanje'),
          }
        ]]
      }
    }
  };

  odgovorPretraga = {
    elements: {
      datum: this.baseChild,
      organVlasti: this.baseChild,
      izdatoU: this.baseChild,
      Pretraga: {
        menu: this.baseMenu
      }
    }
  };

  resenjePretraga = {
    elements: {
      datum: this.baseChild,
      organVlasti: this.baseChild,
      izdatoU: this.baseChild,
      status: {...this.baseChild, ...{
        hasText: true,
        asker: Xonomy.askPicklist,
        askerParameter: ['odustato', 'obavesteno', 'odobreno', 'odbijeno']
      }},
      tipZalbe: {...this.baseChild, ...{
        hasText: true,
        asker: Xonomy.askPicklist,
        askerParameter: ['cutanje', 'odbijanje', 'delimicnost']
      }},
      Pretraga: {
        menu: [...this.baseMenu, ...[
          {
            caption: 'Dodaj <status> tag',
            action: Xonomy.newElementChild,
            actionParameter: '<status></status>',
            hideIf: (jsElement) => this.hideMetapodatak(jsElement, 'status'),
          },
          {
            caption: 'Dodaj <tipZalbe> tag',
            action: Xonomy.newElementChild,
            actionParameter: '<tipZalbe></tipZalbe>',
            hideIf: (jsElement) => this.hideMetapodatak(jsElement, 'tipZalbe'),
          }
        ]]
      }
    }
  };

  izvestajPretraga = {
    elements: {
      datum: this.baseChild,
      organVlasti: this.baseChild,
      izdatoU: this.baseChild,
      godina: this.baseChild,
      Pretraga: {
        menu: [...this.baseMenu, ...[
          {
            caption: 'Dodaj <godina> tag',
            action: Xonomy.newElementChild,
            actionParameter: '<godina></godina>',
            hideIf: (jsElement) => this.hideMetapodatak(jsElement, 'godina'),
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

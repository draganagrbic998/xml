import { Injectable } from '@angular/core';

declare const Xonomy: any;

@Injectable({
  providedIn: 'root'
})
export class XonomyService {

  constructor() { }

  public testSpecification = {
    elements: {
      Fakultet: {
        menu: [{
          caption: 'Add <Naziv>',
          action: Xonomy.newElementChild,
          actionParameter: '<Naziv></Naziv>',
          hideIf: (jsElement) => {
            return jsElement.hasChildElement('Naziv');
          }
        },
        {
          caption: 'Add <GodinaOsnivanja>',
          action: Xonomy.newElementChild,
          actionParameter: '<GodinaOsnivanja></GodinaOsnivanja>',
          hideIf: (jsElement) => {
            return jsElement.hasChildElement('GodinaOsnivanja');
          }
        },
        {
          caption: 'Add <Profesori>',
          action: Xonomy.newElementChild,
          actionParameter: '<Profesori></Profesori>',
          hideIf: (jsElement) => {
            return jsElement.hasChildElement('Profesori');
          }
        },
        {
          caption: 'Add @Id',
          action: Xonomy.newAttribute,
          actionParameter: { name: 'Id', value: '1' },
          hideIf: (jsElement) => {
            return jsElement.hasAttribute('Id');
          }
        }],
        attributes: {
          Id: {
            asker: Xonomy.askString,
            menu: [{
              caption: 'Delete @Id',
              action: Xonomy.deleteAttribute
            }]
          }
        },
      },
      Naziv: {
        mustBeBefore: ['GodinaOsnivanja', 'Profesori'],
        hasText: true,
        oneliner: true,
        // asker: Xonomy.askString,
        asker: Xonomy.askPicklist,
        askerParameter: ['mama', 'tata', 'baba'],
        menu: [{
          caption: 'Delete <Naziv>',
          action: Xonomy.deleteElement
        }]

      },
      GodinaOsnivanja: {
        mustBeBefore: ['Profesori'],
        hasText: true,
        asker: Xonomy.askString,
        oneliner: true,

      },
      Profesori: {
        menu: [{
          caption: 'Add <Profesor>',
          action: Xonomy.newElementChild,
          actionParameter: '<Profesor></Profesor>',

        }]
      },
      Profesor: {
        menu: [{
          caption: 'Add <Ime>',
          action: Xonomy.newElementChild,
          actionParameter: '<Ime></Ime>'
        },
        {
          caption: 'Add <Prezime>',
          action: Xonomy.newElementChild,
          actionParameter: '<Prezime></Prezime>'
        }]
      },
      Ime: {
        mustBeBefore: ['Prezime'],
        hasText: true,
        asker: Xonomy.askString,
        oneliner: true,

      },
      Prezime: {
        hasText: true,
        asker: Xonomy.askString,
        oneliner: true,
      }
    }
  };

}

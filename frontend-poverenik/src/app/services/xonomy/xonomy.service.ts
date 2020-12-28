import { Injectable } from '@angular/core';

declare const Xonomy: any;

@Injectable({
  providedIn: 'root'
})
export class XonomyService {

  constructor() { }

  public odlukaSpecifikacija = {
    elements: {
      Odluka: {
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
            caption: 'Dodaj @clan atribut',
            action: Xonomy.newAttribute,
            actionParameter: { name: 'clan', value: '1' },
            hideIf: (jsElement) => {
              return jsElement.hasAttribute('clan');
            }
          },
          {
            caption: 'Dodaj @stav atribut',
            action: Xonomy.newAttribute,
            actionParameter: { name: 'stav', value: '1' },
            hideIf: (jsElement) => {
              return jsElement.hasAttribute('stav');
            }
          }
        ],
        attributes: {
          clan: {
            menu: [
              {
                caption: 'Obriši @clan atribut',
                action: Xonomy.deleteAttribute
              },
            ]
          },
          stav: {
            menu: [
              {
                caption: 'Obriši @stav atribut',
                action: Xonomy.deleteAttribute
              }
            ]
          }
        }
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

}

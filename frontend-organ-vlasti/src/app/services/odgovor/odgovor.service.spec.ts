import { TestBed } from '@angular/core/testing';

import { OdgovorService } from './odgovor.service';

describe('OdgovorService', () => {
  let service: OdgovorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OdgovorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

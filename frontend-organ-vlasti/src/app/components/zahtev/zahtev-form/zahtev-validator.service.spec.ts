import { TestBed } from '@angular/core/testing';

import { ZahtevValidatorService } from './zahtev-validator.service';

describe('ZahtevValidatorService', () => {
  let service: ZahtevValidatorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ZahtevValidatorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

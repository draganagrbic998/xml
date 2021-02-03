import { TestBed } from '@angular/core/testing';

import { ObavestenjeValidatorService } from './obavestenje-validator.service';

describe('ObavestenjeValidatorService', () => {
  let service: ObavestenjeValidatorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ObavestenjeValidatorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

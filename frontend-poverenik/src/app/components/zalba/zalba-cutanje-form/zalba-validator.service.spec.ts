import { TestBed } from '@angular/core/testing';

import { ZalbaValidatorService } from './zalba-validator.service';

describe('ZalbaValidatorService', () => {
  let service: ZalbaValidatorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ZalbaValidatorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

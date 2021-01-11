import { TestBed } from '@angular/core/testing';

import { IzvestajValidatorService } from './izvestaj-validator.service';

describe('IzvestajValidatorService', () => {
  let service: IzvestajValidatorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IzvestajValidatorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

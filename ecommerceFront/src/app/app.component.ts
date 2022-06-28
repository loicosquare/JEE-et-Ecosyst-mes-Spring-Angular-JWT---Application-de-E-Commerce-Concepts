import { Component, OnInit } from '@angular/core';
import { CatalogueService } from './services/catalogue.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  title = 'ecommerceFront';
  private categories: any;

  constructor(private catService: CatalogueService) {}

  ngOnInit(): void {
    this.getCategories();
  }

  private getCategories() {
    this.catService.getResource('/categories').subscribe(
      (data) => {
        this.categories = data;
      },
      (err) => {
        console.log(err);
      }
    );
  }
}

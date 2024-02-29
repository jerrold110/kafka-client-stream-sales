import csv
from faker import Faker
import random

def generate_sales_data(num_records):
    fake = Faker()
    sales_data = []
    for _ in range(num_records):
        product_id = fake.random_int(min=1, max=1000)
        quantity = fake.random_int(min=1, max=100)
        price = round(random.uniform(10, 1000), 2)
        sales_data.append([product_id, quantity, price])
    return sales_data

def write_to_csv(data, filename):
    with open(filename, 'w', newline='') as csvfile:
        writer = csv.writer(csvfile)
        writer.writerow(['product_id', 'quantity', 'price'])
        writer.writerows(data)

if __name__ == "__main__":
    num_records = 1000
    random.seed(1)
    sales_data = generate_sales_data(num_records)
    write_to_csv(sales_data, 'sales_data.csv')

create type dish_type as enum ('STARTER', 'MAIN', 'DESSERT');
create type unit_type as enum ('PCS','KG','L');
create type ingredient_category as enum ('VEGETABLE', 'ANIMAL', 'MARINE', 'DAIRY', 'OTHER');
create type mouvement_type as enum('IN','OUT');
create type order_type_enum AS ENUM ('EAT_IN', 'TAKE_AWAY');
create type order_status_enum AS ENUM ('CREATED', 'READY', 'DELIVERED');

create table if not exists dish
(
    id        serial primary key,
    name      varchar(255),
    dish_type dish_type,
    price numeric(10,2)
);

create table if not exists ingredient (
    id       serial primary key,
    name     varchar(255),
    price    numeric(10, 2),
    category ingredient_category
);
create table if not exists  DishIngredient (
   id serial primary key,
   id_dish int references dish(id),
   id_ingredient int references ingredient(id),
   quantity_required numeric(10, 2),
   unit unit_type
);

create table if not exists  StockMovement (
  id serial primary key,
  id_ingredient int references ingredient(id),
  quantity numeric(10,2),
  type mouvement_type,
  unit unit_type,
  creation_datetime timestamp
);

create table if not exists "Order"(
  id serial primary key,
  reference varchar(50),
  creation_datetime timestamp
);

create table if not exists DishOrder(
 id serial primary key,
 id_order int references "Order"(id),
 id_dish int references dish(id),
 quantity int
);

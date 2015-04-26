var vertx = require('vertx')

var eb = vertx.eventBus;

var pa = 'test.mongodb';

var traningsaktiviteter = [
	{
		'_id': 0,
		'namn': 'Löpning',
		'traningsekvivalent': 450,
		'minvarde': 1,
		'enhet': 'km'
	},
	{
		'_id': 1,
		'namn': 'Cykling',
		'traningsekvivalent': 200,
		'minvarde': 1,
		'enhet': 'km'
	},
	{
		'_id': 2,
		'namn': 'Simning',
		'traningsekvivalent': 2,
		'minvarde': 200,
		'enhet': 'm',
		'period' : ['2015-05-01', '2015-09-01']
	},
	{
		'_id': 3,
		'namn': 'Säckhoppning',
		'traningsekvivalent': 4,
		'minvarde': 10,
		'enhet': 'm',
		'kontor': 'jkp'
	}
];

var kontor = [
	{
		'_id': 'jkp',
		'namn': 'Jönköping'
	},
	{
		'_id': 'sdl',
		'namn': 'Sundsvall'
	},
	{
		'_id': 'sth',
		'namn': 'Stockholm'
	}
]

var logins = [
	{
		'_id': 'aj',
		'epost': 'anders.johansson@consid.se',
		'losenord': '123'
	},
	{
		'_id': 'bs',
		'epost': 'bjorn.svanmo@consid.se',
		'losenord': 'bs'
	},
	{
		'_id': 'nl',
		'epost': 'niklas.lindblad@consid.se',
		'losenord': 'nl'
	},
	{
		'_id': 'esa',
		'epost': 'esa@ejc.nu',
		'losenord': 'esa'
	},
	{
		'_id': 'jh',
		'epost': 'johan.hanson@consid.se',
		'losenord': 'jh'
	},
	{
		'_id': 'mm',
		'epost': 'mathias.malmberg@consid.se',
		'losenord': 'mm'
	}
];

var anvandare = [
	{
		'_id': 'aj',
		'namn': 'Anders Johansson',
		'kontor': 'jkp'
	},
	{
		'_id': 'bs',
		'namn': 'Björn Svanmo',
		'kontor': 'jkp'
	},
	{
		'_id': 'nl',
		'namn': 'Niklas Lindblad',
		'kontor': 'sdl'
	},
	{
		'_id': 'esa',
		'namn': 'Esbjörn Johansson',
		'kontor': 'jkp'
	},
	{
		'_id': 'jh',
		'namn': 'Johan Hanson',
		'kontor': 'jkp'
	},
	{
		'_id': 'mm',
		'namn': 'Mathias Malmberg',
		'kontor': 'jkp'
	}
];

var traningstillfallenperanvandare = [
	{
		'_id': 'mm',
        'namn': 'Mathias Malmberg',
        'kontorstillhorighet': 'jkp',
        'traningstillfallen': [
			{
				'_id': '8271461287',
				'varde': '4530',
				'datum': '2014-10-01T16:19:34Z',
				'namn': 'Löpning',
				'poang': '531',
				'enhet': 'meter'
			},
			{
				'_id': '8271461287',
				'varde': '6000',
				'datum': '2014-10-04T16:19:34Z',
				'namn': 'Löpning',
				'poang': '531',
				'enhet': 'meter'
			}
		]
    }
];


// First delete everything

eb.send(pa, {action: 'delete', collection: 'traningsaktiviteter', matcher: {}}, function(reply) {
	eb.send(pa, {action: 'delete', collection: 'kontor', matcher: {}}, function(reply) {
		eb.send(pa, {action: 'delete', collection: 'logins', matcher: {}}, function(reply) {
			eb.send(pa, {action: 'delete', collection: 'anvandare', matcher: {}}, function(reply) {
				eb.send(pa, {action: 'delete', collection: 'traningstillfallenperanvandare', matcher: {}}, function(reply) {
					for (var i = 0; i < traningsaktiviteter.length; i++) {
						eb.send(pa, {
							action: 'save',
							collection: 'traningsaktiviteter',
							document: traningsaktiviteter[i]
						});
					}
					
					for (var i = 0; i < kontor.length; i++) {
						eb.send(pa, {
							action: 'save',
							collection: 'kontor',
							document: kontor[i]
						});
					}
					
					for (var i = 0; i < logins.length; i++) {
						eb.send(pa, {
							action: 'save',
							collection: 'logins',
							document: logins[i]
						});
					}
					
					for (var i = 0; i < anvandare.length; i++) {
						eb.send(pa, {
							action: 'save',
							collection: 'anvandare',
							document: anvandare[i]
						});
					}
					
					for(var i = 0; i < traningstillfallenperanvandare.length; i++){
						eb.send(pa, {
							action: 'save',
							collection: 'traningstillfallenperanvandare',
							document: traningstillfallenperanvandare[i]
						});
					}
				});
			});
		});
	});
});


